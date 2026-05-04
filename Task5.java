T5) Fix: Exception Handling in DocumentValidator

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class DocumentValidator {

    private static final Logger logger = LoggerFactory.getLogger(DocumentValidator.class);

    public ValidationResult validate(Document doc) {
        try {

            // FIX 1: Use proper validation handling instead of RuntimeException
            if (doc == null) {
                logger.warn("Validation failed: Document is null");
                return ValidationResult.failure("Document is null");
            }

            String content = doc.extractContent();

            if (content == null || content.isEmpty()) {
                logger.warn("Validation failed: Empty content");
                return ValidationResult.failure("Empty content");
            }

            return runValidationRules(content);

        } catch (Exception e) {

            // FIX 2: Replace printStackTrace with proper logging
            // Log only unexpected errors as ERROR
            logger.error("Unexpected error during validation", e);

            // FIX 3: Avoid returning null → return failure object
            return ValidationResult.failure("Unexpected validation error");
        }
    }

    public void validateBatch(List<Document> docs) {

        for (Document doc : docs) {
            try {

                ValidationResult r = validate(doc);

                // FIX 4: Null-safe check before using result
                if (r != null && r.isValid()) {
                    saveResult(r);
                }

            } catch (Exception e) {

                // FIX 5: Do not swallow exception silently
                logger.error("Error processing document in batch", e);
            }
        }
    }
}
