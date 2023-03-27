public class Extra {

    public static void main(String[] args) {

        LinkedPriorityQueue<String> pq = new LinkedPriorityQueue<>();

        pq.enqueue("A", 0);
        pq.enqueue("E", 20);
        pq.enqueue("B", 5);
        pq.enqueue("D", 15);
        pq.enqueue("C", 10);

        System.out.println(pq.toString());
    }
}
