//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.pentaho.di.trans.steps.elasticsearchbulk;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.ElasticsearchTimeoutException;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.DocWriteRequest.OpType;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.elasticsearchbulk.ElasticSearchBulkMeta.Server;

public class ElasticSearchBulk extends BaseStep implements StepInterface {
    private static final String INSERT_ERROR_CODE = null;
    private static Class<?> PKG = ElasticSearchBulkMeta.class;
    private ElasticSearchBulkMeta meta;
    private ElasticSearchBulkData data;
    TransportClient tc;
    private Client client;
    private String index;
    private String type;
    BulkRequestBuilder currentRequest;
    private int batchSize = 2;
    private boolean isJsonInsert = false;
    private int jsonFieldIdx = 0;
    private String idOutFieldName = null;
    private Integer idFieldIndex = null;
    private Long timeout = null;
    private TimeUnit timeoutUnit;
    private int numberOfErrors;
    private List<IndexRequestBuilder> requestsBuffer;
    private boolean stopOnError;
    private boolean useOutput;
    private Map<String, String> columnsToJson;
    private boolean hasFields;
    private OpType opType;

    public ElasticSearchBulk(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans) {
        super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
        this.timeoutUnit = TimeUnit.MILLISECONDS;
        this.numberOfErrors = 0;
        this.stopOnError = true;
        this.useOutput = true;
        this.opType = OpType.CREATE;
    }

    public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException {
        Object[] rowData = this.getRow();
        if (rowData == null) {
            if (this.currentRequest != null && this.currentRequest.numberOfActions() > 0) {
                this.processBatch(false);
            }

            this.setOutputDone();
            return false;
        } else {
            if (this.first) {
                this.first = false;
                this.setupData();
                this.currentRequest = this.client.prepareBulk();
                this.requestsBuffer = new ArrayList(this.batchSize);
                this.initFieldIndexes();
            }

            try {
                this.data.inputRowBuffer[this.data.nextBufferRowIdx++] = rowData;
                return this.indexRow(this.data.inputRowMeta, rowData) || !this.stopOnError;
            } catch (KettleStepException var6) {
                throw var6;
            } catch (Exception var7) {
                this.rejectAllRows(var7.getLocalizedMessage());
                String msg = BaseMessages.getString(PKG, "ElasticSearchBulk.Log.Exception", new String[]{var7.getLocalizedMessage()});
                this.logError(msg);
                throw new KettleStepException(msg, var7);
            }
        }
    }

    private void setupData() throws KettleStepException {
        this.data.nextBufferRowIdx = 0;
        this.data.inputRowMeta = this.getInputRowMeta().clone();
        this.data.inputRowBuffer = new Object[this.batchSize][];
        this.data.outputRowMeta = this.data.inputRowMeta.clone();
        this.meta.getFields(this.data.outputRowMeta, this.getStepname(), (RowMetaInterface[])null, (StepMeta)null, this, this.repository, this.metaStore);
    }

    private void initFieldIndexes() throws KettleStepException {
        if (this.isJsonInsert) {
            Integer idx = getFieldIdx(this.data.inputRowMeta, this.environmentSubstitute(this.meta.getJsonField()));
            if (idx == null) {
                throw new KettleStepException(BaseMessages.getString(PKG, "ElasticSearchBulk.Error.NoJsonField", new String[0]));
            }

            this.jsonFieldIdx = idx;
        }

        this.idOutFieldName = this.environmentSubstitute(this.meta.getIdOutField());
        if (StringUtils.isNotBlank(this.meta.getIdInField())) {
            this.idFieldIndex = getFieldIdx(this.data.inputRowMeta, this.environmentSubstitute(this.meta.getIdInField()));
            if (this.idFieldIndex == null) {
                throw new KettleStepException(BaseMessages.getString(PKG, "ElasticSearchBulk.Error.InvalidIdField", new String[0]));
            }
        } else {
            this.idFieldIndex = null;
        }

    }

    private static Integer getFieldIdx(RowMetaInterface rowMeta, String fieldName) {
        if (fieldName == null) {
            return null;
        } else {
            for(int i = 0; i < rowMeta.size(); ++i) {
                String name = rowMeta.getValueMeta(i).getName();
                if (fieldName.equals(name)) {
                    return i;
                }
            }

            return null;
        }
    }

    private boolean indexRow(RowMetaInterface rowMeta, Object[] row) throws KettleStepException {
        try {
            IndexRequestBuilder requestBuilder = this.client.prepareIndex(this.index, this.type);
            requestBuilder.setOpType(this.opType);
            if (this.idFieldIndex != null) {
                requestBuilder.setId("" + row[this.idFieldIndex]);
            }

            if (this.isJsonInsert) {
                this.addSourceFromJsonString(row, requestBuilder);
            } else {
                this.addSourceFromRowFields(requestBuilder, rowMeta, row);
            }

            this.currentRequest.add(requestBuilder);
            this.requestsBuffer.add(requestBuilder);
            return this.currentRequest.numberOfActions() >= this.batchSize ? this.processBatch(true) : true;
        } catch (KettleStepException var4) {
            throw var4;
        } catch (NoNodeAvailableException var5) {
            throw new KettleStepException(BaseMessages.getString(PKG, "ElasticSearchBulkDialog.Error.NoNodesFound", new String[0]));
        } catch (Exception var6) {
            throw new KettleStepException(BaseMessages.getString(PKG, "ElasticSearchBulk.Log.Exception", new String[]{var6.getLocalizedMessage()}), var6);
        }
    }

    private void addSourceFromJsonString(Object[] row, IndexRequestBuilder requestBuilder) throws KettleStepException {
        Object jsonString = row[this.jsonFieldIdx];
        if (jsonString instanceof byte[]) {
            requestBuilder.setSource((byte[])((byte[])jsonString), XContentType.JSON);
        } else {
            if (!(jsonString instanceof String)) {
                throw new KettleStepException(BaseMessages.getString("ElasticSearchBulk.Error.NoJsonFieldFormat"));
            }

            requestBuilder.setSource((String)jsonString, XContentType.JSON);
        }

    }

    private void addSourceFromRowFields(IndexRequestBuilder requestBuilder, RowMetaInterface rowMeta, Object[] row) throws IOException {
        XContentBuilder jsonBuilder = XContentFactory.jsonBuilder().startObject();

        for(int i = 0; i < rowMeta.size(); ++i) {
            if (this.idFieldIndex == null || i != this.idFieldIndex) {
                ValueMetaInterface valueMeta = rowMeta.getValueMeta(i);
                String name = this.hasFields ? (String)this.columnsToJson.get(valueMeta.getName()) : valueMeta.getName();
                Object value = row[i];
                if (value instanceof Date && value.getClass() != Date.class) {
                    Date subDate = (Date)value;
                    value = new Date(subDate.getTime());
                }

                if (StringUtils.isNotBlank(name)) {
                    jsonBuilder.field(name, value);
                }
            }
        }

        jsonBuilder.endObject();
        requestBuilder.setSource(jsonBuilder);
    }

    public boolean init(StepMetaInterface smi, StepDataInterface sdi) {
        this.meta = (ElasticSearchBulkMeta)smi;
        this.data = (ElasticSearchBulkData)sdi;
        if (super.init(smi, sdi)) {
            try {
                this.numberOfErrors = 0;
                this.initFromMeta();
                this.initClient();
                return true;
            } catch (Exception var4) {
                this.logError(BaseMessages.getString(PKG, "ElasticSearchBulk.Log.ErrorOccurredDuringStepInitialize", new String[0]) + var4.getMessage());
                return true;
            }
        } else {
            return false;
        }
    }

    private void initFromMeta() {
        this.index = this.environmentSubstitute(this.meta.getIndex());
        this.type = this.environmentSubstitute(this.meta.getType());
        this.batchSize = this.meta.getBatchSizeInt(this);

        try {
            this.timeout = Long.parseLong(this.environmentSubstitute(this.meta.getTimeOut()));
        } catch (NumberFormatException var2) {
            this.timeout = null;
        }

        this.timeoutUnit = this.meta.getTimeoutUnit();
        this.isJsonInsert = this.meta.isJsonInsert();
        this.useOutput = this.meta.isUseOutput();
        this.stopOnError = this.meta.isStopOnError();
        this.columnsToJson = this.meta.getFieldsMap();
        this.hasFields = this.columnsToJson.size() > 0;
        this.opType = StringUtils.isNotBlank(this.meta.getIdInField()) && this.meta.isOverWriteIfSameId() ? OpType.INDEX : OpType.CREATE;
    }

    private boolean processBatch(boolean makeNew) throws KettleStepException {
        ActionFuture<BulkResponse> actionFuture = this.currentRequest.execute();
        boolean responseOk = false;
        BulkResponse response = null;

        try {
            if (this.timeout != null && this.timeoutUnit != null) {
                response = (BulkResponse)actionFuture.actionGet(this.timeout, this.timeoutUnit);
            } else {
                response = (BulkResponse)actionFuture.actionGet();
            }
        } catch (ElasticsearchException var7) {
            String msg = BaseMessages.getString(PKG, "ElasticSearchBulk.Error.BatchExecuteFail", new String[]{var7.getLocalizedMessage()});
            if (var7 instanceof ElasticsearchTimeoutException) {
                msg = BaseMessages.getString(PKG, "ElasticSearchBulk.Error.Timeout", new String[0]);
            }

            this.logError(msg);
            this.rejectAllRows(msg);
        }

        if (response != null) {
            responseOk = this.handleResponse(response);
            this.requestsBuffer.clear();
        } else {
            this.numberOfErrors += this.currentRequest.numberOfActions();
            this.setErrors((long)this.numberOfErrors);
        }

        if (makeNew) {
            this.currentRequest = this.client.prepareBulk();
            this.data.nextBufferRowIdx = 0;
            this.data.inputRowBuffer = new Object[this.batchSize][];
        } else {
            this.currentRequest = null;
            this.data.inputRowBuffer = (Object[][])null;
        }

        return responseOk;
    }

    private boolean handleResponse(BulkResponse response) {
        boolean hasErrors = response.hasFailures();
        if (hasErrors) {
            this.logError(response.buildFailureMessage());
        }

        int errorsInBatch = 0;
        if (hasErrors || this.useOutput) {
            Iterator var4 = response.iterator();

            while(var4.hasNext()) {
                BulkItemResponse item = (BulkItemResponse)var4.next();
                if (item.isFailed()) {
                    this.logDetailed(item.getFailureMessage());
                    ++errorsInBatch;
                    if (this.getStepMeta().isDoingErrorHandling()) {
                        this.rejectRow(item.getItemId(), item.getFailureMessage());
                    }
                } else if (this.useOutput) {
                    if (this.idOutFieldName != null) {
                        this.addIdToRow(item.getId(), item.getItemId());
                    }

                    this.echoRow(item.getItemId());
                }
            }
        }

        this.numberOfErrors += errorsInBatch;
        this.setErrors((long)this.numberOfErrors);
        int linesOK = this.currentRequest.numberOfActions() - errorsInBatch;
        if (this.useOutput) {
            this.setLinesOutput(this.getLinesOutput() + (long)linesOK);
        } else {
            this.setLinesWritten(this.getLinesWritten() + (long)linesOK);
        }

        return !hasErrors;
    }

    private void addIdToRow(String id, int rowIndex) {
        this.data.inputRowBuffer[rowIndex] = RowDataUtil.resizeArray(this.data.inputRowBuffer[rowIndex], this.getInputRowMeta().size() + 1);
        this.data.inputRowBuffer[rowIndex][this.getInputRowMeta().size()] = id;
    }

    private void echoRow(int rowIndex) {
        try {
            this.putRow(this.data.outputRowMeta, this.data.inputRowBuffer[rowIndex]);
        } catch (KettleStepException var3) {
            this.logError(var3.getLocalizedMessage());
        } catch (ArrayIndexOutOfBoundsException var4) {
            this.logError(var4.getLocalizedMessage());
        }

    }

    private void rejectRow(int index, String errorMsg) {
        try {
            this.putError(this.getInputRowMeta(), this.data.inputRowBuffer[index], 1L, errorMsg, (String)null, INSERT_ERROR_CODE);
        } catch (KettleStepException var4) {
            this.logError(var4.getLocalizedMessage());
        } catch (ArrayIndexOutOfBoundsException var5) {
            this.logError(var5.getLocalizedMessage());
        }

    }

    private void rejectAllRows(String errorMsg) {
        for(int i = 0; i < this.data.nextBufferRowIdx; ++i) {
            this.rejectRow(i, errorMsg);
        }

    }

    private void initClient() throws UnknownHostException {
        Builder settingsBuilder = Settings.builder();
        settingsBuilder.put(Builder.EMPTY_SETTINGS);
        this.meta.getSettingsMap().entrySet().stream().forEach((s) -> {
            settingsBuilder.put((String)s.getKey(), this.environmentSubstitute((String)s.getValue()));
        });
        PreBuiltTransportClient tClient = new PreBuiltTransportClient(settingsBuilder.build(), new Class[0]);
        Iterator var3 = this.meta.getServers().iterator();

        while(var3.hasNext()) {
            Server server = (Server)var3.next();
            tClient.addTransportAddress(new TransportAddress(InetAddress.getByName(this.environmentSubstitute(server.getAddress())), server.getPort()));
        }

        this.client = tClient;
    }

    private void disposeClient() {
        if (this.client != null) {
            this.client.close();
        }

    }

    public void dispose(StepMetaInterface smi, StepDataInterface sdi) {
        this.meta = (ElasticSearchBulkMeta)smi;
        this.data = (ElasticSearchBulkData)sdi;

        try {
            this.disposeClient();
        } catch (Exception var4) {
            this.logError(var4.getLocalizedMessage(), var4);
        }

        super.dispose(smi, sdi);
    }
}
