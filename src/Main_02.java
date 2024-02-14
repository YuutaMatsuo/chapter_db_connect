import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main_02 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String key;

		System.out.println("商品検索システム");
		while (true) {
			System.out.println("商品IDを入力してください");
			System.out.print("商品ID >>");
			key = sc.nextLine();
			if (!((Integer.parseInt(key)) >= 0 && (Integer.parseInt(key)) <= 8)) {
				System.out.println("商品が見つかりませんでした。もう一度入力してください");
				continue;
			}
			break;
		}

		// データベース接続のために必要な変数を3つ準備する
		// 接続するDB
		String url = "jdbc:postgresql://localhost:5432/shop";
		// 接続するユーザー名
		String user = "postgres";
		// 接続するパスワード
		String password = "root";

		try {
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
			// データベースとの接続 その１
//			Connection con = DriverManager.getConnection(url,user,password);
			// データベースとの接続 その２ close処理が自動で行われる
			try (Connection con = DriverManager.getConnection(url, user, password);) {
				Statement st = con.createStatement();

				// SQLに送信するクエリのkeyの左右にシングルクォーテーションを入れ忘れないように注意する！
				ResultSet rs = st
						.executeQuery("select * from shohin where shohin_id ='"
				+ key 
				+ "'order by shohin_id asc");

				rs.next();
				String shohinMei = rs.getString("shohin_mei");
				System.out.println("商品名:" + shohinMei);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
