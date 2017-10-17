import java.io.UnsupportedEncodingException;

public class TestJave {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String a = new String("閲婃斁".getBytes("GBK"),"UTF-8");
		System.out.println(a);
	}
}
