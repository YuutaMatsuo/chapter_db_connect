
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
			
		} catch() {
			
		}
	}
}
