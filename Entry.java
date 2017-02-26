import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

class Entry{
	
	public static String file;
	public static String text;
	public static Boolean extension;
	
	public Boolean getExtension(String file){
		

		try {
            FileReader fr = new FileReader(file);
            BufferedReader br = 
                new BufferedReader(fr);

            while((text = br.readLine()) != null) {
                System.out.println(text);
            }br.close();
		}
		catch(IOException ex)
		{
			System.out.println("File not found");
		}
		
		try {
			if( (file.substring(file.lastIndexOf(".") + 1)) == "java")
			{
				return true;
			}
		} catch (Exception e) {}
		return false;

	
	}
	public static void main( String[] args ) 
	{
		file = args[0];
		Entry e = new Entry();
		extension = e.getExtension(file);
	}
}
