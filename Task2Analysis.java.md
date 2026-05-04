Task 2 – ConcurrentModificationException Analysis

1. Cause of ConcurrentModificationException

ConcurrentModificationException occurs when a collection (like ArrayList) 
is modified while it is being iterated using an iterator or for-each loop.

Example:
- Removing or adding elements inside a loop
- One thread modifies while another is iterating

---

2. Code pattern that caused this error (Line 142)

Most likely pattern:

for (Transaction t : transactions) {
    if (condition) {
        transactions.remove(t);   //  causes exception
    }
}

Reason:
For-each loop internally uses an iterator.
Direct modification of the list breaks iterator consistency.


// 3. Minimal Fix:
    // FIX:
    public void fixUsingJava8(List<Transaction> transactions) {

        transactions.removeIf(t -> condition(t));
    }

    // Dummy condition method (example)
    private boolean condition(Transaction t) {
        return t == null; // example condition
    }
}