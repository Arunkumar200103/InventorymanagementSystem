package firstproject;

import java.io.*;
import java.util.*;

class Product {
    private String name;
    private int quantity;
    private double price;

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + "," + quantity + "," + price;
    }
}

public class InventoryManagementSystem {
    private static final String FILE_NAME = "products.txt";
    private static List<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        loadProducts();
        displayMenu();
    }

    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nInventory Management System");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. List All Products");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    updateProduct(scanner);
                    break;
                case 3:
                    deleteProduct(scanner);
                    break;
                case 4:
                    listProducts();
                    break;
                case 5:
                    saveProducts();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
        scanner.close();
    }

    private static void addProduct(Scanner scanner) {
        System.out.println("\nAdding Product");
        System.out.print("Enter product name: ");
        String name = scanner.next();
        System.out.print("Enter product quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        products.add(new Product(name, quantity, price));
        System.out.println("Product added successfully.");
    }

    private static void updateProduct(Scanner scanner) {
        System.out.println("\nUpdating Product");
        System.out.print("Enter product name: ");
        String name = scanner.next();
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                System.out.print("Enter new quantity: ");
                int quantity = scanner.nextInt();
                System.out.print("Enter new price: ");
                double price = scanner.nextDouble();
                product.setQuantity(quantity);
                product.setPrice(price);
                System.out.println("Product updated successfully.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.println("\nDeleting Product");
        System.out.print("Enter product name: ");
        String name = scanner.next();
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                System.out.println("Product deleted successfully.");
                return;
            }
        }
        System.out.println("Product not found.");
    }

    private static void listProducts() {
        System.out.println("\nListing All Products");
        for (Product product : products) {
            System.out.println(product.getName() + " - Quantity: " + product.getQuantity() + ", Price: $" + product.getPrice());
        }
    }

    private static void loadProducts() {
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    double price = Double.parseDouble(parts[2]);
                    products.add(new Product(name, quantity, price));
                }
            }
        } catch (FileNotFoundException e) {
            // File does not exist yet, ignore
        }
    }

    private static void saveProducts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Product product : products) {
                writer.println(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
