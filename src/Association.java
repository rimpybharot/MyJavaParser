import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Association {

	private List<ArrayList<String>> finalRel = new ArrayList<ArrayList<String>>();
	private HashMap<String, ArrayList<String>> associationsList = new HashMap<>();

	public Association(HashMap<String, ArrayList<String>> associationsList) {

		this.associationsList = associationsList;
		for(Entry<String, ArrayList<String>> association : associationsList.entrySet()){

			ArrayList<String> otherClasses = new ArrayList<>();

			for(Entry<String, ArrayList<String>> association1 : associationsList.entrySet()){

				otherClasses.add(association1.getKey().toString());

			}
			otherClasses.remove(association.getKey());

			//System.out.println("Now " + association.getKey()+" \nOthers " + otherClasses);


			ArrayList<String> otherClassInAssociation = association.getValue();


			for(int i=0;i<otherClassInAssociation.size();i=i+3){

				//System.out.println("get rel with other "+ otherClassInAssociation.get(i));

				String key = otherClassInAssociation.get(i);

				if(otherClasses.contains(otherClassInAssociation.get(i))){
					
					if(this.associationsList.get(key).contains(association.getKey())){
						//System.out.println("bi");
						
						int otherRel = associationsList.get(key).indexOf(association.getKey());
						String m1 = associationsList.get(key).get(otherRel+1);
						
						setRelationShip(association.getKey(), otherClassInAssociation.get(i),
								m1, otherClassInAssociation.get(i+1));
					}
					else{
						//System.out.println("uni but present somewhere");
						setRelationShip(association.getKey(), otherClassInAssociation.get(i), "" , 
								otherClassInAssociation.get(i+1));
					}
				}
				else{
					//System.out.println("uni");

					setRelationShip(association.getKey(), otherClassInAssociation.get(i), "" , 
							otherClassInAssociation.get(i+1));
				}

			}



		}



	}



	private void setRelationShip(String rel1, String rel2, String rel1M, String rel2M) {

		//System.out.println("new \n Lets make relations");
		
		//System.out.println(rel1);
		//System.out.println(rel2);
		//System.out.println(rel1M);
		//System.out.println(rel2M);

		
		boolean relationExists = false;
		if(!this.finalRel.isEmpty()){
			for(ArrayList<String> someRel : this.finalRel){
				
				//System.out.println("final rel not empty");

				Pattern p1 = Pattern.compile("\\b"+rel1+"\\b");
				Matcher m1 = p1.matcher(someRel.get(0));
				Pattern p2 = Pattern.compile("\\b"+rel2+"\\b");
				Matcher m2 = p2.matcher(someRel.get(3));
				Pattern p3 = Pattern.compile("\\b"+rel2+"\\b");
				Matcher m3 = p3.matcher(someRel.get(0));
				Pattern p4 = Pattern.compile("\\b"+rel1+"\\b");
				Matcher m4 = p4.matcher(someRel.get(3));

				Pattern r1 = Pattern.compile("\""+rel1M+"\"--");
				Matcher rm1 = r1.matcher(someRel.get(1));
				//System.out.println("\""+rel1M+"\"--");
				//System.out.println(someRel.get(1));
				
				
				Pattern r2 = Pattern.compile("\""+rel2M+"\"");
				Matcher rm2 = r2.matcher(someRel.get(2));
				//System.out.println("\""+rel2M+"\"");
				//System.out.println(someRel.get(2));

				Pattern r3 = Pattern.compile("\""+rel2M+"\"--");
				Matcher rm3 = r3.matcher(someRel.get(1));
				//System.out.println("\""+rel2M+"\"--");
				//System.out.println(someRel.get(1));

				Pattern r4 = Pattern.compile("\""+rel1M+"\"");
				Matcher rm4 = r4.matcher(someRel.get(2));
				//System.out.println("\""+rel1M+"\"");
				//System.out.println(someRel.get(2));

				if((m1.find() && m2.find() && rm1.find() && rm2.find())
				|| ( m3.find() && m4.find() && rm3.find() && rm4.find())){
					//System.out.println("Relation is there");
					relationExists = true;
					break;
				}

			}
		}
		if(!relationExists){
			ArrayList<String> rl = new ArrayList<>();

			rl.add(0, rel1);
			if(rel1M!=""){
				rl.add(1, "\""+rel1M+"\"--");

			}
			else{
				rl.add(1, rel1M+"--");
			}
			if(rel2M!=""){
				rl.add(2, "\""+rel2M+"\"");

			}
			else{
				rl.add(2, rel2M);
			}
			rl.add(3, rel2);
			//System.out.println("Relation made");
			relationExists = false;

			//System.out.println(rl);
			this.finalRel.add(rl);
		}

//		for(ArrayList<String> rl : this.finalRel){
//			System.out.println(rl);
//		}
	}



	public List<ArrayList<String>> getRelations() {
		this.finalRel.removeAll(Collections.singleton(null));
		return this.finalRel;
	}



}


