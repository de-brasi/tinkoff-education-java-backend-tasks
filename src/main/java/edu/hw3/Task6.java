package edu.hw3;

import java.util.PriorityQueue;

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
            stockStorage.removeIf(
                (suspect) -> (
                    (suspect.getPrice().equals(stock.getPrice()))
                        && (suspect.getCompanyName().equals(stock.getCompanyName()))
                )
            );
        }

        @Override
        public Stock mostValuableStock() {
            return stockStorage.peek();
        }

        private final PriorityQueue<Stock> stockStorage = new PriorityQueue<>(
            (lhs, rhs) -> {
                return -1 * lhs.getPrice().compareTo(rhs.getPrice());
            }
        );
    }
}
