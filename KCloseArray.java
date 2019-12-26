import java.util.Scanner;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read in k, which represents the maximum
        // distance between a number's current position
        // and sorted position
        int k = Integer.parseInt(sc.nextLine());
        
        // Read in the list of numbers
        int[] numbers;
        String input = sc.nextLine();
        if (input.equals("")) {
            numbers = new int[0];
        } else {    
            String[] numberStrings = input.split(" ");
            numbers = new int[numberStrings.length];
            for (int i = 0; i < numberStrings.length; i++) {
                numbers[i] = Integer.parseInt(numberStrings[i]);
            }
        }

        // Sort the list
        sort(numbers, k);

        // Print the sorted list
        StringBuilder resultSb = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            resultSb.append(new Integer(numbers[i]).toString());
            if (i < numbers.length - 1) {
                resultSb.append(" ");
            }
        }
        System.out.println(resultSb.toString());
    }
    
    public static void sort(int[] numbers, int k) {
        //array is already sorted
        if(k==0){
          return;
        }
        MinHeap heap = new MinHeap();
        //copies first k+1 numbers into heap
        for(int i=0;i<k+1;i++){
          heap.insert(numbers[i]);
        }
        int currIndex=0;
        for(int i=k+1;i<numbers.length;i++){
          //places each number in correct position
          numbers[currIndex]=heap.extractMin();
          //adds next smallest number to heap
          heap.insert(numbers[i]);
          //increments the index of sorted array
          currIndex++;
        }
        //places rest of the numbers to correct position
        while(!heap.isEmpty()){
          numbers[currIndex]=heap.extractMin();
          currIndex++;
        }
    }
}
class MinHeap {
  private ArrayList<Integer> data;

  public MinHeap() {
    data = new ArrayList<>();
  }

  private int getParent(int index) { 
    return (index - 1) / 2; 
  }
  
  private int getLeftChild(int index) { 
    return index * 2 + 1; 
  } 

  private int getRightChild(int index) { 
    return index * 2 + 2;  
  } 

  private void swap(int indexA, int indexB) {
    int temp = data.get(indexA);
    data.set(indexA, data.get(indexB));
    data.set(indexB, temp);
  }

  public void print() {
    System.out.println(data.toString());
  }

  int getMin() throws Exception {
    if (data.size() > 0) {
      return data.get(0);
    }
    throw new Exception ("Heap is empty!");
  }

  void insert(int val) {
    data.add(val);
    int currentIndex = data.size() - 1;
    while (data.get(currentIndex) < data.get(getParent(currentIndex))) {
      swap(currentIndex, getParent(currentIndex));
      currentIndex = getParent(currentIndex);
    }
  }

  // Remove AND return min element
  public int extractMin() {
    int min = data.get(0);
    swap(0, data.size() - 1);
    data.remove(data.size() - 1);
    minHeapify(0);
    return min;
  }
  public boolean isEmpty(){
    return data.isEmpty();
  }

  void minHeapify(int index) {
    // If it's a leaf - return
    if (getLeftChild(index) >= data.size()) {
      return;
    } 
    // If there's only a left child
    if (getRightChild(index) >= data.size()) {
      if (data.get(index) > data.get(getLeftChild(index))) {
        swap(index, getLeftChild(index));
        return;
      }
    }
    // If there's two children, check if parent bigger than either child
    else if (data.get(index) > data.get(getLeftChild(index)) ||
      data.get(index) > data.get(getRightChild(index))
    ) {
      if (data.get(getLeftChild(index)) < data.get(getRightChild(index))) {
        swap(index, getLeftChild(index));
        minHeapify(getLeftChild(index));
      } else {
        swap(index, getRightChild(index));
        minHeapify(getRightChild(index));
      }
    }
  }

}