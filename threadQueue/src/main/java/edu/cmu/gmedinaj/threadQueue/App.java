package edu.cmu.gmedinaj.threadQueue;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

public class App 
{
    Queue<String> queue;
    public static void main(String[] args) {
        new App();
    }

    class Pusher extends Thread {
        Random rand = new Random();
        String id;
        private static final int SECOND_IN_MILI = 1000;

        public Pusher(int id) {
            this.id = "(" + Integer.toString(id) + ")";
        } 
        
        private String generateString() throws InterruptedException {
            String origin = "Pushed by: " + this.id + " ";
            Thread.sleep(SECOND_IN_MILI);
            System.out.println("Pushing..." + this.id);
            return origin + Integer.toString(this.rand.nextInt(0, 1000));
        }

        @Override
        public void run() {
            while (true) {
                try {
                    App.this.add(this.generateString());
                } catch (Exception e) {
                    System.out.println(this.id + " Error while adding string");
                }
            }
        }
    }

    class Popper extends Thread {
        
        String id;
        private static final int SECOND_IN_MILI = 1000;

        public Popper(int id) {
            this.id = "(" + Integer.toString(id) + ") ";
        }
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(this.id + " Popping...");
                    Thread.sleep(SECOND_IN_MILI);
                    System.out.println(this.id + " Popped! " + App.this.pop());
                } catch (Exception e) {
                    System.out.println(this.id + " Queue Empty");
                }
            }
        }
    }

    public App() {
        this.queue = new LinkedList<>();
        Pusher pusherA = new Pusher(0);
        Pusher pusherB = new Pusher(1);
        Popper popper1 = new Popper(2);
        Popper popper2 = new Popper(3);
        pusherA.start();
        pusherB.start();
        popper1.start();
        popper2.start();
    }
    
    public synchronized String pop() throws NoSuchElementException {
        return this.queue.remove();
    }

    public synchronized void add(String s) throws IllegalStateException {
        this.queue.add(s);
    }
}
