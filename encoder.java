import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

class encoder
{
	int pathIndex = 0;
	int counter = 1;
	int temp;
	private String line;
	int[] currPath = new int[150];
	int currPathIndex = 0;
	int y = -1;
	public static void main(String[] args)
	{
		try
		{
			encoder e = new encoder();
			e.run(args);
		}
		catch(Exception e)
		{
			//System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public void run(String[] args)
	{
		//String fileName = args[0];
		
		Trie mwTrie = new Trie();
		Node currentNode = null;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("Dictionary.txt"));
			while((line = br.readLine()) != null) 
			{
				char cha = line.charAt(0);
				mwTrie.buildDict(counter,cha);
				counter++;
			}
			
			
			//FileInputStream in =  new FileInputStream(fileName);
			InputStreamReader in = new InputStreamReader(System.in);
			//FileOutputStream out = new FileOutputStream("output.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter("Encoded_text.txt"));
			//BufferedWriter br = new BufferedWriter(new FileWriter("packed.txt"));

			for(int i = 0; i<currPath.length;i++)
			{
				currPath[i] = -1;
			}
			int j;
			char c;
			//loop until end of file reading in one char at a time
            //while ((j = in.read()) != -1) 
			while ((j = in.read()) != -1) 
			{
				
				
				//get the character
				c = (char)j;
				
				int i = 0;
				//if current path is empty
				//dont add new node here as will always be in current dict
				if(currPath[0] == -1)
				{
					
					int k = 1;
					//loop through dict until correct char is found
					//add the index to the current path
					while(mwTrie.TRIE[k].letter != c)
					{
						k++;
					}
					//give the curr path the index of the value
					
					currPath[0] = k;
					currPathIndex = 1;
				}
				//current path is not empty
				else
				{
					
					currPathIndex = 1;
					//make the currentNode the beginning of the current run

					currentNode = mwTrie.TRIE[currPath[0]];

					//while this point in currpath exists
					while(currPath[currPathIndex] != -1)
					{
						currentNode = currentNode.child[currPath[currPathIndex]];
						currPathIndex++;
					}

					
					//currentNode points to the last point in run

					//check through its children for a match
					boolean found = false;
					int x;

					for(x = 0; x< currentNode.child.length;x++)
					{
						if(currentNode.child[x] != null)
						{
							if(currentNode.child[x].letter == c)
							{
								y = x;
								found = true;
							}
						}
						
					}

					//if a match was found, add it to currpath
					if(found)
					{
						//.out.println("Match Found");
						int r = 0;
						while(currPath[r] != -1)
						{
							r++;
						}
						if(y!= -1)
						{
							currPath[r] = y;
							currPathIndex = r;
							y = -1;
						}
						


					}
					//if no match is found then a new character has been found, add it to the trie and reset currpath
					else
					{

						mwTrie.addNode(counter,c,currPath);

						counter++;
						
						int t = currentNode.value;
						String s = Integer.toString(t);
						writer.write(s + System.lineSeparator());
						
						//reset currpath
						for(int q = 0; q < currPath.length;q++)
						{
							currPath[q] = -1;
						}
						currPathIndex = 0;
						
						//add this item to start of next run
						
						
						int k = 1;
						//loop through dict until correct char is found
						//add the index to the current path
						while(mwTrie.TRIE[k].letter != c)
						{
							k++;
						}
						
						currPath[0] = k;
						currPathIndex = 1;
						
						
						
					}

				}
				
				
            }

			currPathIndex = 1;
					//make the currentNode the beginning of the current run

			currentNode = mwTrie.TRIE[currPath[0]];

					//while this point in currpath exists
			while(currPath[currPathIndex] != -1)
			{
				currentNode = currentNode.child[currPath[currPathIndex]];
				currPathIndex++;
			}
			
			int t = currentNode.value;
					String s = Integer.toString(t);
					writer.write(s);

			in.close();
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}


class Node
{
	public char letter;		//letter the node holds
	public int value;		//numerical value of the letter
	public int MaxSize = 28;
	public boolean exists;
	public Node[]  child = new Node[MaxSize];
	public Node[]  temp = new Node[MaxSize];
	public Node(char Letter,int Value,boolean Exists)
	{
		letter = Letter;
		value = Value;
		exists = Exists;
	}
	
	
}

class Trie
{
	//has an array of nodes for the initial dictionary
	//public  Node[]  TRIE = new Node[28];
	public Master masterNode;
	public  Node[]  TRIE = new Node[2];
	public int maxSize = 2;
	public Node[]  TEMP = new Node[maxSize];
	int temp;
	String tempString;
	
	public Trie()
	{

	}
	//Builds the initial dictionary of all 26 numerical characters and a space
	public void buildDict(int curr, char cha)
	{
		if(curr >= maxSize)
		{
			TEMP = new Node[maxSize];
			for(int i = 0;i<TRIE.length;i++)
			{
				TEMP[i] = TRIE[i];
			}
			maxSize = maxSize*2;
			TRIE = new Node[maxSize];
			
			for(int j = 0;j<TEMP.length;j++)
			{
				TRIE[j] = TEMP[j];
			}
		}
		TRIE[curr] = new Node(cha,curr,false);
	}
	
	public void addNode(int curr, char cha, int[] arr)
	{
		try
		{
			
			
			Node currNode;
			currNode = TRIE[arr[0]];
			for(int i = 0; i < arr.length; i++)
			{
				if(arr[i] != -1)
				{
					if(i == 0)
					{
						//arr at point 0
						currNode = TRIE[arr[i]];
					}
					else
					{
						currNode = currNode.child[arr[i]];
						//arr at point i until arr is finished
					}
				}
				
			}
			//currNode points to the final node in the given array
			//now add a new node
			int i = 0;

			
			//if the current node has children
			if(currNode.child[0] != null)
			{
				while(currNode.child[i] != null)
				{
					i++;
				}
			}
			else
			{
				i = -1;
			}
			
			//now i is the final node in the child array of currNode
			
			i++;
			if(i >=2)
			{
				i--;
			}
			if(i > currNode.MaxSize)
			{
				currNode.temp = new Node[currNode.MaxSize];
				
				for(int x = 0;x<currNode.child.length;x++)
				{
					currNode.child[x] = currNode.temp[x];
				}
				currNode.MaxSize = currNode.MaxSize*2;
				currNode.child = new Node[currNode.MaxSize];
				
				for(int j = 0;j<TEMP.length;j++)
				{
					currNode.child[j] = currNode.temp[j];
				}
			}
			//System.out.println(i + " " + cha);
			currNode.child[i] = new Node(cha,curr,true);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		//loops through the array
		
		//return 1;
		
	}
	
	//public Node getNode()
	//{
		
	//}
	
	public void printTrie()
	{
		int j;
		for(int i = 0; i < TRIE.length; i++)
		{
			j = 0;
			System.out.println(TRIE[i].letter + ": ");
			while(TRIE[i].child[j] != null)
			{
				System.out.print(TRIE[i].child[j].letter + " ");
				j++;
			}
			System.out.println("");
		}
		//System.out.println("");
		
		
	}
	
	
	public int[] checkChar(char c, int[] path)
	{
		return null;
	}
	
}