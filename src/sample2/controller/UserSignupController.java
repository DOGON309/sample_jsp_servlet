package sample2.controller;

/*
 * import java.io.*; - ライブラリの読み込み
 * これを読み込むことによってPrintWriter等が使えるようになる
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * import javax.servlet.http.HttpSession;
 * HttpSessionが使えるようになる
 */
import javax.servlet.http.HttpSession;

@WebServlet("/sample2/signup")
public class UserSignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserSignupController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 *
		 */
		HttpSession session = request.getSession(false);
		/*
		 *
		 */
		if (session != null) {
			session.invalidate();
		}
		request.getRequestDispatcher("/sample2/jsp/UserSignupController.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 文字列の username って言う変数を宣言
		 * 文字列の password って言う変数を宣言
		 */
		String username;
		String password;
		int error = 0;
		/*
		 * ブラウザでusernameとpasswordを入力してサインアップのボタンを押すと
		 * requestクラスの中に入力された内容が保存される。
		 * サーバーは、request.getParameter()関数で入力された内容を見ることができる。
		 * 引数、たとえば下のような場合はusernameを引数に入力する
		 * <input name="username">
		 *
		 * request,getParameter("username");
		 * ↑これで読み込んだ情報を文字列で宣言した username変数に代入する（使いやすくするため）
		 *
		 */
		username = request.getParameter("username");
		password = request.getParameter("password");
		/*
		 *username変数内に空白がないか指定の文字数より少なかったり多かったりしてないかを確認する
		 */
		if (username.isEmpty()) {

			request.setAttribute("message", "ユーザー名を入力してください");
			error++;

		}else if (!username.matches("[!-~]{3,16}")) {

			request.setAttribute("message", "ユーザー名を正しく入力してください");
			error++;

		}

		if (password.isEmpty()) {

			request.setAttribute("message", "パスワードを入力してください");
			error++;

		}else if (!password.matches("[a-z]{6,32}")) {

			request.setAttribute("message", "パスワードを正しく入力してください");
			error++;

		}
		/*
		 *message変数にエラー文が入ってないか確認する
		 *List.size()でList型の変数内に何個の要素が入っているかを判別する
		 */
		if (error == 0) {
			/*
			 * SQLとのセッションを始める時にエラーが出ていないか判別するために
			 * 変数を宣言してあとから判別できるようにしておく
			 */
			Connection conn = null;
			/*
			 * Class.forName()やpstmt.executeUpdate()などで状況的な要因でエラーが出る可能性がある為
			 * try/catch/finallyで囲みエラー出た場合の処理を書く
			 */
			try {
				/*
				 * Class.forName()は…呪文です…
				 * ドライバを読込、下のDriverManegerにドライバの位置を指定するため必要
				 */
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				/*
				 * DriverManage.getConnection(データベースのURL, データベースのユーザー名, データベースのパスワード)
				 * データベースのURL（引数）は、自分パソコン内のSQLを使うのであればlocalhost、外部のSQLを使う場合など外部のSQLのURLを指定する
				 * データベースのユーザー名（引数）は、データベースに保存したユーザー名を入力する
				 * データベースのパスワード（引数）は、データベースに登録したパスワードを入力する
				 */
				conn = DriverManager.getConnection("jdbc:mysql://localhost/sample_jsp_servlet", "username", "password");
				/*
				 *
				 */
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO account (username, password) VALUES (?,?)");
				pstmt.setString(1, username);
				pstmt.setString(2, password);

				int num = pstmt.executeUpdate();

				conn.close();
				pstmt.close();

				HttpSession session = request.getSession(true);

				session.setAttribute("userid", num);

				request.getRequestDispatcher("/sample2/jsp/Home.jsp").forward(request, response);

			}catch (ClassNotFoundException e) {

				e.printStackTrace();

			}catch (SQLException e) {

				e.printStackTrace();

			}catch (Exception e) {

				e.printStackTrace();

			}

		}else {

			request.getRequestDispatcher("/sample2/jsp/UserSignupController.jsp").forward(request, response);

		}
	}

}
