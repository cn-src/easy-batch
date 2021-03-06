package org.jeasy.batch.tutorials.advanced.restart;

import org.jeasy.batch.core.record.Batch;
import org.jeasy.batch.core.record.Record;
import org.jeasy.batch.core.writer.RecordWriter;

import java.util.Random;

public class BuggyWriter<P> implements RecordWriter<P> {

    private RecordWriter<P> recordWriter;

    public BuggyWriter(RecordWriter<P> recordWriter) {
        this.recordWriter = recordWriter;
    }

    @Override
    public void open() throws Exception {
        recordWriter.open();
    }

    @Override
    public void writeRecords(Batch<P> batch) throws Exception {
        boolean crash = new Random().nextBoolean();
        if (crash) {
            for (Record<P> record : batch) {
                if (record.getHeader().getNumber().equals(4L)) {
                    throw new Exception("Unable to write records");
                }
            }
        }
        recordWriter.writeRecords(batch);
    }

    @Override
    public void close() throws Exception {
        recordWriter.close();
    }
}
