import java.util.ArrayList;
import java.util.List;

// Represents a product in the database
class ProductDatabase {
    private int productId;
    private String productName;
    private double productPrice;

    // Constructor to initialize the product details
    public ProductDatabase(int productId, String productName, double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }
    // Getter and Setters for the product attributes
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}

// Represents an order made by a customer
class CustomerAgentOrder {
    private int productId;
    private int quantity;

    // Constructor to initialize the order details
    public CustomerAgentOrder(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters for the order attributes
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

// Represents an offer made by a retailer
class RetailerAgentOffer {
    private int productId;
    private double productPrice;
    private int stock;

    // Constructor to initialize the offer details
    public RetailerAgentOffer(int productId, double productPrice, int stock) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.stock = stock;
    }

    // Getter and Setters for the offer attributes
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

// Represents a customer agent
class CustomerAgent {
    private int customerId;

    // Constructor to initialize the customer
    public CustomerAgent(int customerId) {
        this.customerId = customerId;
    }

    // Getters and Setters for the customer ID
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    // Evaluate the offers received from retailer agents through the broker agent
    public void evaluateOffers(List<RetailerAgentOffer> offers) {
        RetailerAgentOffer bestOffer = null;

        // Find the offer with the lowest price
        for (RetailerAgentOffer offer : offers) {
            if (bestOffer == null || offer.getProductPrice() < bestOffer.getProductPrice()) {
                bestOffer = offer;
            }
        }

        // Print the best offer or a message if no offers were received
        if (bestOffer != null) {
            System.out.println("Best offer: " + bestOffer.getProductId() + " - £" + bestOffer.getProductPrice());
        } else {
            System.out.println("No offers received");
        }
    }
}

// Represents a retailer agent
class RetailerAgent {
    private int retailerId;
    private List<ProductDatabase> products;

    // Getters and Setters for the retailer ID
    public RetailerAgent(int retailerId) {
        this.retailerId = retailerId;
        this.products = new ArrayList<>();
    }

    public int getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

    // Checks if the retailer can fulfil a customer order
    public boolean canFulfillOrder(CustomerAgentOrder customerAgentOrder) {
        int requestedProductId = customerAgentOrder.getProductId();
        int requestedQuantity = customerAgentOrder.getQuantity();

        // Check if the retailer has the requested product in stock
        for (ProductDatabase product : products) {
            if (product.getProductId() == requestedProductId && product.getProductPrice() > 0 && product.getProductPrice() <= requestedQuantity) {
                return true;
            }
        }

        return false;
    }

    // Receives an offer invitation from the broker agent
    public void receiveOfferInvitation(RetailerAgentOffer offer) {
        if (offer.getStock() > 0) {
            System.out.println("Received offer: " + offer.getProductId() + " - £" + offer.getProductPrice());
        }
    }
}

// Represents a broker agent
class BrokerAgent {
    private List<CustomerAgent> customers;
    private List<RetailerAgent> retailers;

    // Constructor to initiate the broker with customers and retailers
    public BrokerAgent() {
        this.customers = new ArrayList<>();
        this.retailers = new ArrayList<>();
    }

    // Adds a customer to the broker's customer list
    public void addCustomer(CustomerAgent customer) {
        customers.add(customer);
    }

    // Adds a retailer to the broker's retailer list
    public void addRetailer(RetailerAgent retailer) {
        retailers.add(retailer);
    }

    // Receives a customer order and evaluates offers from retailers
    public void receiveCustomerOrder(CustomerAgentOrder order) {
        List<RetailerAgentOffer> offers = new ArrayList<>();

        // Iterates through retailers to find those offers that can fulfill the order
        for (RetailerAgent retailer : retailers) {
            if (retailer.canFulfillOrder(order)) {
                RetailerAgentOffer offer = new RetailerAgentOffer(order.getProductId(), 10.0, 5);
                offers.add(offer);
            }
        }

        // If offers are available. evaluate them for all customers
        if (!offers.isEmpty()) {
            for (CustomerAgent customer : customers) {
                customer.evaluateOffers(offers);
            }
        }
    }
}

// Main class representing the e-commerce scenario
public class E_Commerce_Scenario {
    public static void main(String[] args) {
        // Create instances of products, retailers, customers, and the broker
        ProductDatabase product1 = new ProductDatabase(1, "Product 1", 9.99);
        ProductDatabase product2 = new ProductDatabase(2, "Product 2", 19.99);

        RetailerAgent retailer1 = new RetailerAgent(1);
        RetailerAgent retailer2 = new RetailerAgent(2);

        // Simulates receiving offer invitations by retailers
        retailer1.receiveOfferInvitation(new RetailerAgentOffer(1, 10.0, 5));
        retailer2.receiveOfferInvitation(new RetailerAgentOffer(2, 20.0, 10));

        CustomerAgent customer1 = new CustomerAgent(1);
        CustomerAgent customer2 = new CustomerAgent(2);

        BrokerAgent broker = new BrokerAgent();
        broker.addCustomer(customer1);
        broker.addCustomer(customer2);
        broker.addRetailer(retailer1);
        broker.addRetailer(retailer2);

        // Create a customer order and pass it to the broker
        CustomerAgentOrder order = new CustomerAgentOrder(1, 5);
        broker.receiveCustomerOrder(order);
    }
}
