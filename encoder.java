import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

class encoder
{
	int counter;
	int temp;
	private String line;
	int[] currPath = new int[150];
	int currPathIndex = 0;
	public static void main(String[] args)
	{
		try
		{
			encoder e = new encoder();
			e.run(args);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void run(String[] args)
	{
		String fileName = args[0];
		
		Trie mwTrie = new Trie();
		Node currentNode;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("Dictionary.txt"));
			while((line = br.readLine()) != null) 
			{
				char cha = line.charAt(0);
				mwTrie.buildDict(counter,cha);
				counter++;
			}
			
			
			FileInputStream in =  new FileInputStream(fileName);
			FileOutputStream out = new FileOutputStream("output.txt");
			
			for(int i = 0; i<currPath.length;i++)
			{
				currPath[i] = -1;
			}
			
			int j;
			char c;
			//loop until end of file reading in one char at a time
            while ((j = in.read()) != -1) 
			{
				//get the character
				c = (char)j;
				int i = 0;
				//if current path is empty
				//dont add new node here as will always be in current dict
				if(currPath[0] == -1)
				{
					int k = 0;
					//loop through dict until correct char is found
					//add the index to the current path
					while(mwTrie.TRIE[k].letter != c)
					{
						//System.out.println(c);
						k++;
					}
					//give the curr path the index of the value
					currPath[0] = k;
					currPathIndex = 1;
					//System.out.println(mwTrie.TRIE[currPath[0]].letter);
					System.out.println("start Value: " + currPath[0]);
				}
				//current path is not empty
				else
				{
					
					int pathIndex = 1;
					//make the currentNode the beginning of the current run
					currentNode = mwTrie.TRIE[currPath[0]];
					
					//while this point in currpath exists
					while(currPath[pathIndex] != -1)
					{
						currentNode = currentNode.child[currPath[pathIndex]];
					}
					//currentNode points to the last point in run
					
					//check through its children for a match
					boolean found = false;
					int x;
					int y = 0;
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
						System.out.println("Match Found");
						currPath[currPathIndex] = y;
						System.out.println("Current Y val: " + y);
						int r = 0;
						System.out.print("currPath: ");
						while(currPath[r]!= -1)
						{
							System.out.print(currPath[r] + " ");
							r++;
						}
						System.out.println("");
					}
					//if no match is found then a new character has been found, add it to the trie and reset currpath
					else
					{
						System.out.println("current char: " + c);
						mwTrie.addNode(counter,c,currPath);
						System.out.println("Added '" + c + "' at " + currPathIndex);
						//reset currpath
						for(int q = 0; q<currPath.length;q++)
						{
							currPath[i] = -1;
						}
						currPathIndex = 0;
					}
					
					
					
					//System.out.println(c);
				}
				
				
            }
			in.close();
			out.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}

class Node
{
	public char letter;	//letter the node holds
	public int value;		//numerical value of the letter
	public Node[]  child = new Node[27];
	public Node(char Letter,int Value)
	{
		letter = Letter;
		value = Value;
		
	}
	
	
}

class Trie
{
	//has an array of nodes for the initial dictionary
	public  Node[]  TRIE = new Node[27];
	int temp;
	String tempString;
	

	//Builds the initial dictionary of all 26 numerical characters and a space
	public void buildDict(int curr, char cha)
	{
		TRIE[curr] = new Node(cha,curr);
	}
	
	public void addNode(int curr, char cha, int[] arr)
	{
		//loops through the array
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
		currNode.child[i] = new Node(cha,curr);
		//return 1;
		
	}
	
	//public Node getNode()
	//{
		
	//}
	
	public void printTrie()
	{
	
	}
	
	public int[] checkChar(char c, int[] path)
	{
		return null;
	}
	
}