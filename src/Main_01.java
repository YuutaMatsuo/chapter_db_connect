import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main_01 {
	public static void main(String[] args) {
		// JavaからDBに接続するためにはJDBCドライバが必要
		// JDBCドライバのダウンロード
		// 「PostgreSQL JDBC」で検索 → PostgreSQL JDBC Driver
		
		// 接続前準備
		// ①ダウンロードしたjarファイルをプロジェクトに追加する(「lib」フォルダを新規作成」)
		// ②ビルドパスを通す(追加したjarファイルを「右クリック」→「ビルドパスの追加」)
		
		// 実際にJDBCプログラムを作ってみよう！
		
		// ↓どこにあるデータベースなのか？
		String url = "jdbc:postgresql://localhost:5432/shop";
		// ↓接続するユーザー名
		String user = "postgres"; //DBeaverの「編集・接続」で確認できる
		// ↓接続パスワード
		String password = "root";
		
		try {
			// PostgreSQLのドライバをDriverManagerに登録する
			Class.forName("org.postgresql.Driver");
			
			// データベースに接続する(戻り値としてConnectionインスタンスが返ってくる)
			Connection connection = DriverManager.getConnection(url,user,password);
			
			// ここからSQL文絡み
			// ↓SQLを実行するためのインスタンスを生成する
			Statement statement = connection.createStatement();
			
			// データベースサーバにSQL文を送信して実行する(結果はResultSetインスタンスとして返ってくる)
			ResultSet resultSet = statement.executeQuery(
				"select * from shohin order by shohin_id asc");
			
			// ★余裕があったらこちらも！
			// ■INSERT文を実行すると…？
			// int型の数値が戻り値として返ってくる！(更新行数)
			// int rows = statement.executeQuery("insert into テーブル名(列リスト) values (値リスト)");
			
			// 初めはカーソルが0行目の位置を指している
			// カーソルを1行目に移動する
			resultSet.next(); // 次の行を指して…
			String shohinMei = resultSet.getString("shohin_mei"); //取得するクエリのデータ型に合わせてメソットを使い分ける
			String shohinId = resultSet.getString("shohin_id");
			Integer hanbaiTanka = resultSet.getInt("hanbai_tanka");
			// ただし、getIntメソッドは内部でNULL値を「0」として返す
			// 直前のget～()の取得結果がSQL上でnullかどうかをResultSet.wasNull()で検知できる
			resultSet.next();
			String shohinMei2 = resultSet.getString("shohin_mei");
			
			
			// ↑1行目のレコードの「商品名」が入る
			System.out.println("商品ID:" + shohinId);
			System.out.println("商品名:" + shohinMei);
			System.out.println("販売単価:" + hanbaiTanka);
			
			System.out.println("商品２:" + shohinMei2);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
