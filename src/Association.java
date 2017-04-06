import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class Association {

	public List<ArrayList<String>> finalRel = new ArrayList<ArrayList<String>>();
	private String relative = "";
	private String sindexval = "";
	private String directional = "";


	public Association(HashMap<String, ArrayList<String>> associationsList, List<String> classifierNames) {

				for(Entry<String, ArrayList<String>> entry : associationsList.entrySet()){
					System.out.println("Working on " + entry.getKey());
					for(Entry<String, ArrayList<String>> entry1 : associationsList.entrySet()){
						if(!(entry.getKey()==entry1.getKey())){
							System.out.println("Getting comparison "+entry1.getKey());
							if(entry1.getValue().contains(entry.getKey())){
								System.out.println(entry.getKey()+" is present in " + entry1.getKey());
								int index = entry1.getValue().indexOf(entry.getKey());
								relative = entry1.getValue().get(index+1);
								if(entry.getValue().contains(entry1.getKey())){
									System.out.println(entry1.getKey()+" is present in " + entry.getKey());
									int sindex = entry.getValue().indexOf(entry1.getKey());
									sindexval = entry.getValue().get(sindex+1);
									directional = "bi";
								}
								else{
									directional = "uni";
								}
								System.out.println("Relationship is " + directional + "directional");

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
						else{
							for(String classifier : classifierNames){
								
//								System.out.println(classifier);
								
								if(entry.getValue().contains(classifier)){
									
									int index = entry.getValue().indexOf(classifier);
									relative = entry.getValue().get(index+1);					
									
									System.out.println(relative);

									sindexval = "";

									ArrayList<String> rl = new ArrayList<>();

									rl.add(0, entry.getKey());
									rl.add(1, "--\""+relative+"\"");
//									if(sindexval!=""){
//										rl.add(2, "\""+sindexval+"\"");
//
//									}
//									else{
//										rl.add(2, sindexval);
//									}
									rl.add(2, classifier);
									finalRel.add(rl);
								}
							}
							
						}
					}
				}
				
				
			}
	


	
	
}
