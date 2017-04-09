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

		//otherRelationships();
	}


	public void otherRelationships(){
		boolean relExist = false;
		List<ArrayList<String>> tempRel = new ArrayList<ArrayList<String>>();

		for(Entry<String, ArrayList<String>> association : this.associationsList.entrySet()){
			System.out.println(association.getKey());
			System.out.println(association.getValue());

			ArrayList<String> associationForCurrent = association.getValue();
			
			for(int i=0;i<associationForCurrent.size();i=i+2){

				relExist = false;

				if(!tempRel.isEmpty()){
					for(ArrayList<String> rel : tempRel){
						Pattern p1 = Pattern.compile("\\b"+associationForCurrent.get(i)+"\\b");
						Matcher m1 = p1.matcher(rel.get(0));
						Pattern p2 = Pattern.compile("\\b"+association.getKey()+"\\b");
						Matcher m2 = p2.matcher(rel.get(3));
						Pattern p3 = Pattern.compile("\\b"+associationForCurrent.get(i)+"\\b");
						Matcher m3 = p3.matcher(rel.get(3));
						Pattern p4 = Pattern.compile("\\b"+association.getKey()+"\\b");
						Matcher m4 = p4.matcher(rel.get(0));

						if((!(m1.find() )&& !(m2.find())) || (!(m3.find() )&& !(m4.find()))){
							relExist = true;
							break;
						}
						else{
							relative = associationForCurrent.get(i+1);
							System.out.println(relative);
							
						}
					}
				}

				if(!relExist){
					ArrayList<String> rl = new ArrayList<>();

					rl.add(0, association.getKey());
					rl.add(1, "\""+relative+"\"--");
					rl.add(2, "");
					rl.add(3, associationForCurrent.get(i));
					finalRel.add(rl);

				}
			}




		}


		this.finalRel.addAll(tempRel);
	}


}
