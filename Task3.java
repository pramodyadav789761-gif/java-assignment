3) Fix: Thread-safety in BankStatementBatchProcessor

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BankStatementBatchProcessor {

    // FIX: Use AtomicInteger instead of int to ensure thread-safe increment
    // Reason: processedCount++ is not atomic (read + modify + write)
    private AtomicInteger processedCount = new AtomicInteger(0);

    public void process(List<StatementRecord> records) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (StatementRecord record : records) {
            executor.submit(() -> {
                processRecord(record);

                // FIX: Atomic increment ensures no race condition
                processedCount.incrementAndGet();
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);
    }

    public int getProcessedCount() {
        // FIX: Return actual int value from AtomicInteger
        return processedCount.get();
    }
}
