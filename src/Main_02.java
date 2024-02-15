import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main_02 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String key;
		boolean check = false;

		System.out.println("商品検索システム");
		System.out.println("商品IDを入力してください");
		System.out.print("商品ID >>");
		key = sc.nextLine();

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
//				Statement st = con.createStatement();
//
//				// SQLに送信するクエリのkeyの左右にシングルクォーテーションを入れ忘れないように注意する！
//				ResultSet rs = st
//						.executeQuery("select * from shohin where shohin_id ='"
//				+ key 
//				+ "'order by shohin_id asc");
//
//				rs.next();
//				String shohinMei = rs.getString("shohin_mei");
//				System.out.println("商品名:" + shohinMei);
				
				// 実行の流れ…
				// ●Statement
				// ①SQL文の解析＋実行

				// 実は、SQL文に文字列連結をさせるのはあまりよろしくない・・・
				// （DELETE文などを入れてデータを消されてしまう可能性）
				// SQLインジェクション…不正な入力値からDBに攻撃をすること

				// ■SQLインジェクション対策として「preparedStatement」を使用する
				// テキストP.322～

				String sql = "select * from shohin where shohin_id = ? order by shohin_id asc";

				// 戻り値はPreparedStatementインスタンスだが、メソッド名はprepareStatementなので注意
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, key); // 1つ目のプレイスホルダーへ「key」（入力値）を流し込む
//				ps.setInt(2, 20); // 2つ目の売れ椅子ホルダーへ「20」を流し込む
				ResultSet rs = ps.executeQuery(); // SQL文の送信
				
				// 実行の流れ
				// PreparedStatement -- SQLの実行回数が多くなればなるほどStatementを使うよりも早く実行できる
				// ①SQL文の解析（値が流し込まれたらいつでも実行OK！） 最初の１回だけ行われる
				// ②SQL文に必要な値を流し込む
				// ③SQL文を実行する

				rs.next();
				String shohinMei = rs.getString("shohin_mei");
				System.out.println("商品名:" + shohinMei);

				// ■閉じる処理
				rs.close();
				ps.close(); // PreparedStatementもクローズ対象となる
				sc.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
