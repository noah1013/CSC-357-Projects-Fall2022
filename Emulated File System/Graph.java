import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;


class Graph{

    public static void main(String args[]) throws FileNotFoundException{
        validateArgs(args);
        LinkedList[] graph = createAdjacencyList(args[0]);
        Stack<Integer> tree = new Stack<Integer>();
        boolean[] visited = new boolean[graph.length];
        
        DFS(0, graph, visited, tree);
        LinkedList[] reverseGraph = createReverseGraph(graph);

        ArrayList<LinkedList> scc = new ArrayList<LinkedList>();
        printDFS(reverseGraph, visited, tree, scc);
        System.out.println(scc.size() + " Strongly Connected Component(s):");
        for(int i = 0; i < scc.size(); i++){
            scc.get(i).printList();
            System.out.println();
        }
        

        PrintStack(tree);
        
    }


    static LinkedList[] createAdjacencyList(String filename) throws FileNotFoundException{
        int max = 0;
        int currVertex;
        File file = new File(filename);
        Scanner scan = new Scanner(file);
        Scanner lines;
        String line = "";
        
            
        while(scan.hasNext()){
            line = scan.nextLine();
            //System.out.println(line);
            if(line.equals(""))
                break;
            
            lines = new Scanner(line);
            lines.useDelimiter(", ");
            
            for(int i = 0; i < 2; i++){
                currVertex = lines.nextInt();
                if(currVertex > max)
                max = currVertex;   
            }
            
            lines.close();
        } 
        
        scan.close();


        LinkedList[] graphList = new LinkedList[max+1]; 
        for(int i = 0; i < graphList.length; i++){
            graphList[i] = new LinkedList();
        }
        
        scan = new Scanner(file);
        
        while(scan.hasNext()){
            line = scan.nextLine();
            if(line.equals(""))
                break;
            
            lines = new Scanner(line);
            
            lines.useDelimiter(", ");
            currVertex = lines.nextInt();
            graphList[currVertex].add(lines.nextInt());
            lines.close();
        }
        scan.close();
        return graphList;
    }

    static LinkedList[] createReverseGraph(LinkedList[] graph){
        LinkedList[] reverse = new LinkedList[graph.length];
        for(int i = 0; i < reverse.length; i++){
            reverse[i] = new LinkedList();
        }
        for(int i = 0; i < reverse.length; i++){
            for(int j = 0; j < graph[i].size; j++){
                reverse[graph[i].get(j)].add(i);
            }
        }
        return reverse;
    }

    static void DFS(int v, LinkedList[] graph, boolean[] visited, Stack<Integer> tree){
        if(!isEmpty(graph)){
            emptyVisited(visited);
            for(int i = 0; i < graph.length; i++){
                if(!visited[i])
                    explore(v, graph, visited, tree);
            }
        }
        
    }

    static void explore(int v, LinkedList[] graph, boolean[] visited, Stack<Integer> tree){
        visited[v] = true;
        for(int i = 0; i < graph[v].size; i++){
            if(!visited[graph[v].get(i)]){
                explore(graph[v].get(i), graph, visited, tree);
            }
        }
        tree.push(v);
    }

    

    static ArrayList<LinkedList> printDFS(LinkedList[] graph, boolean[] visited, Stack<Integer> tree, ArrayList<LinkedList> scc){
        if(!isEmpty(graph)){
            emptyVisited(visited);
            int i  = 0;
            while(!tree.empty()){
                int v = tree.peek();
                tree.pop();
                //System.out.println(v + "\n");
                if(!visited[v]){
                    scc.add(new LinkedList());
                    printExplore(v, graph, visited, scc, i);
                    i++;
                }
            }
        }
        return scc;
    }

    static void printExplore(int v, LinkedList[] graph, boolean[] visited, ArrayList<LinkedList> scc, int j){
        visited[v] = true;
        scc.get(j).add(v);
        for(int i = 0; i < graph[v].size; i++){
            if(!visited[graph[v].get(i)]){
                printExplore(graph[v].get(i), graph, visited, scc, j);
            }
        }
    }

    static void emptyVisited(boolean[] visiited){
        for(int i = 0; i < visiited.length; i++){
            visiited[i] = false;
        }
    }

    static void validateArgs(String args[]){
        if(args.length != 1){
            System.exit(1);
        }
    }

    static boolean isEmpty(LinkedList[] graph){
        return (graph.length == 1) && (graph[0].isEmpty());
    }

    static void PrintStack(Stack<Integer> s){

        Stack<Integer> temp = new Stack<Integer>();
        while (s.empty() == false)
        {
            temp.push(s.peek());
            s.pop();
        }  
    
        while (temp.empty() == false)
        {
            int t = temp.peek();
            System.out.print(t + " ");
            temp.pop();
    
            // To restore contents of
            // the original stack.
            s.push(t); 
        }
    }   
}

class node{
    int value;
    node next;

    node(int v){
        value = v;
        next = null;
    }
}

class LinkedList{
    node head;
    node tail;
    int size = 0;

    void add(int data){
        node newVer = new node(data);
        if(isEmpty()){
            head = newVer;
            tail = newVer;
        }
        else{
            if(data < head.value){
                newVer.next = head;
                head = newVer;
            }
            else if(data > tail.value){
                tail.next = newVer;
                tail = newVer;
            }
            else{
                node curr = head;
                while(curr.next != null ){
                    if(data > curr.value){
                        break;
                    }
                    curr = curr.next;
                }
                if(curr.next != null)
                    newVer.next = curr.next;
                curr.next = newVer;

            }
            
        }
        size++;
    }

    int get(int index){
        node curr = head;
        for(int i = 0; i < index; i++){
            curr = curr.next;
        }
        return curr.value;
    }

    boolean isEmpty(){
        return size == 0;
    }

    void printList(){
        if(head!= null){
            node curr = head;
            System.out.print(curr.value);
            for(int i = 0; i < size - 1; i++){
                curr = curr.next; 
                System.out.print(", " + curr.value);
            }
        }
        
    }
}   