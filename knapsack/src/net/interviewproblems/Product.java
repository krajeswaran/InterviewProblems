package net.interviewproblems;

import java.util.Arrays;

/**
 *  A simple container for each product
 */
public class Product {
    int productId;
    int price;
    int length;
    int width;
    int height;
    int weight;
    int volume;
    boolean shouldPick;

    // YOLO
    public Product(String[] args) {
        if (args == null || args.length < 6) {
            // we are done
            System.err.println("Problem parsing line: " + Arrays.toString(args));
            return;
        }

        this.productId = Integer.parseInt(args[0]);
        this.price = Integer.parseInt(args[1]);
        this.length = Integer.parseInt(args[2]);
        this.width = Integer.parseInt(args[3]);
        this.height = Integer.parseInt(args[4]);
        this.weight = Integer.parseInt(args[5]);
        this.volume = this.weight * this.height * this.width;
    }
}
