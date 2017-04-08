import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Association {

	public List<ArrayList<String>> finalRel = new ArrayList<ArrayList<String>>();
	private String relative = "";
	private String sindexval = "";
	private String directional = "";
	HashMap<String, ArrayList<String>> associationsList = new HashMap<>();
	List<String> classifierNames = new ArrayList<>();

	public Association(HashMap<String, ArrayList<String>> associationsList, List<String> classifierNames) {

		this.associationsList = associationsList;
		this.classifierNames = classifierNames;

		for(Entry<String, ArrayList<String>> entry : associationsList.entrySet()){
			for(Entry<String, ArrayList<String>> entry1 : associationsList.entrySet()){
				if(!(entry.getKey()==entry1.getKey())){
					if(entry1.getValue().contains(entry.getKey())){
						int index = entry1.getValue().indexOf(entry.getKey());
						relative = entry1.getValue().get(index+1);
						if(entry.getValue().contains(entry1.getKey())){
							int sindex = entry.getValue().indexOf(entry1.getKey());
							sindexval = entry.getValue().get(sindex+1);
							directional = "bi";
						}
						else{
							directional = "uni";
						}
						boolean relationExists = false;
						if(!this.finalRel.isEmpty()){
							for(ArrayList<String> somerel : this.finalRel){
								if((somerel.contains(entry1.getKey()))&&(somerel.contains(entry.getKey()))){
									relationExists = true;
								}
								else{
								}
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

		otherRelationships();
	}


	public void otherRelationships(){
		boolean relExist = false;
		List<ArrayList<String>> tempRel = new ArrayList<ArrayList<String>>();
		for(Entry<String, ArrayList<String>> entry : this.associationsList.entrySet()){
//			System.out.println(entry.getKey());
			ArrayList<String> assoc = entry.getValue();
			for(ArrayList<String> someRel : this.finalRel){
//				System.out.println(someRel.size());
				for (int i = 0; i < assoc.size(); i++){
					if(i % 2 == 0) {
//						System.out.println("Getting relation with " + assoc.get(i));

//							Pattern p = Pattern.compile("\\b"+entry.getValue().get(i)+"\\b");
//							Matcher m = p.matcher(assoc.get(0));
//							Pattern p1 = Pattern.compile("\\b"+entry.getKey()+"\\b");
//							Matcher m1 = p1.matcher(assoc.get(3));
//							if(!(m.find() )&& !(m1.find())){
//								relExist = true;
//							}
//						}
//
					}
//					if(!relExist){
//						relative = entry.getValue().get(i+1);					
//						ArrayList<String> rl = new ArrayList<>();
//						rl.add(0, entry.getKey());
//						rl.add(1, "--\""+relative+"\"");
//						rl.add(2, entry.getValue().get(i));
//						tempRel.add(rl);	
//
//					}

				}
			}

		}

		//		else{
		//			relative = entry.getValue().get(i+1);					
		//			ArrayList<String> rl = new ArrayList<>();
		//			rl.add(0, entry.getKey());
		//			rl.add(1, "--\""+relative+"\"");
		//			rl.add(2, entry.getValue().get(i));
		//			tempRel.add(rl);	
		//		}

		this.finalRel.addAll(tempRel);
	}


}
