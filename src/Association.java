import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Association {

	private List<ArrayList<String>> finalRel = new ArrayList<ArrayList<String>>();
	private String relative = "";
	private String sindexval = "";
	private String directional = "";


	public Association(HashMap<String, ArrayList<String>> associationsList) {
		for(Entry<String, ArrayList<String>> entry : associationsList.entrySet()){
			
			
			System.out.println("Working on " + entry.getKey());
			 
			
			for(Entry<String, ArrayList<String>> entry1 : associationsList.entrySet()){
				
//				System.out.println("Getting second keys " + entry1.getKey());
				
				if(!(entry.getKey()==entry1.getKey())){
					
					System.out.println("Getting comparison "+entry1.getKey());
					System.out.println(entry1.getValue());
					
					if(entry1.getValue().contains(entry.getKey())){
						
						System.out.println("contains");
						
						
						int index = entry1.getValue().indexOf(entry.getKey());
						relative = entry1.getValue().get(index+1);
						if(entry.getValue().contains(entry1.getKey())){
							int sindex = entry.getValue().indexOf(entry1.getKey());
							sindexval = entry.getValue().get(sindex+1);
							directional = "uni";
						}
						else{
							directional = "bi";
						}
						boolean relationExists = false;
						for(ArrayList<String> somerel : finalRel){
							if((somerel.contains(entry1.getKey()))&&(somerel.contains(entry.getKey()))){
								relationExists = true;
							}
						}
						if(!relationExists){
							ArrayList<String> rl = new ArrayList<>();

							rl.add(0, entry.getKey());
							rl.add(1, "\""+relative+"\"--");
							if(sindexval!=""){
								rl.add(2, "\""+sindexval+"\"");

							}
							else{
								rl.add(2, sindexval);
							}
							rl.add(3, entry1.getKey());
							finalRel.add(rl);
						}
					}
				}
			}
		}

	}

}
