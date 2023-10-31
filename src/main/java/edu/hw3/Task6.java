package edu.hw3;

import java.util.PriorityQueue;

interface StockMarket {
    /** Добавить акцию */
    public void add(Stock stock);
    /** Удалить акцию */
    public void remove(Stock stock);
    /** Самая дорогая акция */
    public Stock mostValuableStock();
}

public class Task6 {
    private Task6() {
        // not allowed
    }
    public static class Market implements StockMarket {
        @Override
        public void add(Stock stock) {
            stockStorage.add(stock);
        }

        @Override
        public void remove(Stock stock) {
            final var toRemoveObj = stockStorage.removeIf(
                (suspect) -> (
                    (suspect.getPrice() == stock.getPrice())
                        && (suspect.getCompanyName() == stock.getCompanyName())
                )
            );
            stockStorage.remove(stock);
        }

        @Override
        public Stock mostValuableStock() {
            return stockStorage.peek();
        }

        private final PriorityQueue<Stock> stockStorage = new PriorityQueue<Stock>(
            (lhs, rhs) -> {
                return -1 * lhs.getPrice().compareTo(rhs.getPrice());
            }
        );
    }
}
