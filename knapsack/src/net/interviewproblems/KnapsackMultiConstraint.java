package net.interviewproblems;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class KnapsackMultiConstraint {
    private static final String CSV_FILENAME = "products.csv";
    private static final int TOTE_HEIGHT = 35;
    private static final int TOTE_WIDTH = 30;
    private static final int TOTE_LENGTH = 45;
    private static final int TOTE_VOLUME = TOTE_HEIGHT * TOTE_WIDTH * TOTE_LENGTH;

    private static int knapsack(ArrayList<Product> products) {
        if (products == null || products.size() < 1) {
            return 0;
        }

        int N = products.size();
        int[][] k = new int[N + 1][TOTE_VOLUME + 1];

        for (int n = 1; n <= N; n++) {
            for (int v = 1; v <= TOTE_VOLUME; v++) {
                int option1 = k[n-1][v];

                int option2 = Integer.MIN_VALUE;
                Product currentProduct = products.get(n-1);
                if (currentProduct.volume <= v) {
                    option2 = currentProduct.price + k[n-1][v-currentProduct.volume];
                }

                k[n][v] = Math.max(option1, option2);
            }
        }

        int productIdCount = 0;
        for (int n = N, v = TOTE_VOLUME; n > 0; n--) {
            if(k[n][v] != k[n-1][v]) {
                products.get(n-1).shouldPick = true;
                productIdCount += products.get(n-1).productId;
                v -= products.get(n-1).volume;
            }
        }

        return productIdCount;
    }

    private static ArrayList<Product> parseCsv() {
        String lines;
        String cvsSplitBy = ",";

        InputStream is = null;
        ArrayList<Product> productList = new ArrayList<>();
        try {
            is = KnapsackMultiConstraint.class.getResourceAsStream(CSV_FILENAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((lines = br.readLine()) != null) {
                String[] fields = lines.split(cvsSplitBy);

                if (willProductFitTote(fields)) {
                    productList.add(new Product(fields));
                }
            }
        } catch (IOException  e) {
            System.err.println("OOPS! can't read products.csv file, exception : " + e);
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                System.err.println("Error closing products.csv : " + e);
            }
        }

        return productList;
    }

    public static boolean willProductFitTote(String[] args) {
        int length = Integer.parseInt(args[2]);
        int width = Integer.parseInt(args[3]);
        int height = Integer.parseInt(args[4]);

        return (width <= TOTE_WIDTH) && (height <= TOTE_HEIGHT) && (length <= TOTE_LENGTH) && (length * height * width <= TOTE_VOLUME);
    }

    private static void printDebugInfo(ArrayList<Product> products) {
        int productIdCount = 0, weightCount = 0, priceCount = 0;
        System.out.println("Picking product IDs");
        for (Product product : products) {
            if (product.shouldPick) {
                System.out.print(product.productId + " ");
                productIdCount += product.productId;
                weightCount += product.weight;
                priceCount += product.price;
            }
        }
        System.out.printf("\nTotal counts == ProdIdCnt: %d, Wght: %d, Price: %d \n", productIdCount, weightCount, priceCount);
    }

    public static void main(String[] args) {
        ArrayList<Product> products = parseCsv();
        System.out.println("Product ID sum : " + knapsack(products));
        printDebugInfo(products);
    }
}
