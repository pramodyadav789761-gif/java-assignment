public List<LoanAccount> getOverdueLoans(List<LoanAccount> accounts) {
    
    // FIX: Initialize result list to avoid NullPointerException
    List<LoanAccount> result = new ArrayList<>();
    
    // FIX: Use Java 8 Optional to handle null accounts list
    Optional.ofNullable(accounts).ifPresent(list -> {
        for (LoanAccount account : list) {
            
            // FIX: Skip null account objects
            if (Objects.isNull(account)) {
                continue;
            }
            
            // FIX: Handle possible null dueDate using Optional
            Optional.ofNullable(account.getDueDate()).ifPresent(dueDate -> {

                if (dueDate.before(new Date())
                        // FIX: Use Double.compare to avoid precision issue for zero balance
                        && Double.compare(account.getOutstandingBalance(), 0.0) > 0) {

                    result.add(account);
                }
            });
        }
    });
    return result;
}
