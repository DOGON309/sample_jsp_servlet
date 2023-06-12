package sample1.controller;

/*
 * import java.io.*; - ライブラリの読み込み
 * これを読み込むことによってPrintWriter等が使えるようになる
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/*
 *import java.util.*;
 *Listなどのライブラリが使えるようになる
 */
import java.util.ArrayList;
import java.util.List;

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

@WebServlet("/sample1/signup")
public class UserSignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserSignupController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 *--Java
		 *	response - クラス
		 *	setContentType - 関数
		 *
		 * --servlet/jsp
		 * response.setContentType("text/html; cahrset="UTF-8") - 文字化け防止
		 * これがないと利用するブラウザによって漢字ばかりの意味が分からないけどサイトになる
		 *
		 * 例えばこんなん → 縺薙ｓ縺ｫ縺｡繧
		 *
		 */
		response.setContentType("text/html; charset=UTF-8");
		/*
		 * --Java
		 * PrintWriter out - PrintWriterクラスだけが入れるoutと言う変数を宣言
		 * response - クラス
		 * getWriter() - 関数
		 *
		 * --servlet/jsp
		 * PrintWriter out = response.getWriter(); - Writerクラスを継承したPrintWriterクラスを返してきます
		 * ブラウザに対して文字列をかえすためのクラスがPrintWriter
		 *
		 */
		PrintWriter out = response.getWriter();
		/*
		 * --Java
		 * out - PrintWriterクラスが入ってる変数
		 * Println - PrintWriterクラスに入ってるprintln()関数 … System.out.println()とは違うよ…
		 */
		out.println("<html>");
		out.println("<head>");
		out.println("<title>sample1_singup</title>");
		out.println("<link rel=\"stylesheet\" href=\"/sample_jsp_servlet/css/bootstrap.css\"");
		out.println("</head>");
		out.println("<body>");
		out.println("<script src=\"/sample_jsp_servlet/js/bootstrap.min.js\"></script>");
		out.println("<div class=\"container\">");
		out.println("<h1>SIGNUP</h1>");
		out.println("<form action=\"/sample_jsp_servlet/sample1/signup\" method=\"post\">");
		out.println("<div class=\"input-group mb-3\">");
		out.println("<span class=\"input-group-text\">USERNAME</span>");
		out.println("<input class=\"form-control\" type=\"text\" name=\"username\" placeholder=\"Username\" minlength=3 maxlength=16 required>");
		out.println("</div>");
		out.println("<div class=\"input-group mb-3\">");
		out.println("<span class=\"input-group-text\">PASSWORD</span>");
		out.println("<input class=\"form-control\" type=\"password\" name=\"password\" placeholder=\"Password\" minlength=6 maxlength=32 required>");
		out.println("</div>");
		out.println("<button class=\"btn btn-primary\" type=\"submit\">サインアップ</button>");
		out.println("<a href=\"/sample_jsp_servlet/sample1\"><button class=\"btn btn-secondary\" type=\"button\">キャンセル</button></a>");
		out.println("</form>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 *--Java
		 *	response - クラス
		 *	setContentType - 関数
		 *
		 * --servlet/jsp
		 * response.setContentType("text/html; cahrset="UTF-8") - 文字化け防止
		 * これがないと利用するブラウザによって漢字ばかりの意味が分からないけどサイトになる
		 *
		 * 例えばこんなん → 縺薙ｓ縺ｫ縺｡繧
		 *
		 */
		response.setContentType("text/html; charset=UTF-8");
		/*
		 * --Java
		 * PrintWriter out - PrintWriterクラスだけが入れるoutと言う変数を宣言
		 * response - クラス
		 * getWriter() - 関数
		 *
		 * --servlet/jsp
		 * PrintWriter out = response.getWriter(); - Writerクラスを継承したPrintWriterクラスを返してきます
		 * ブラウザに対して文字列をかえすためのクラスがPrintWriter
		 *
		 */
		PrintWriter out = response.getWriter();
		/*
		 * 文字列の username って言う変数を宣言
		 * 文字列の password って言う変数を宣言
		 */
		String username;
		String password;
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
		 * List型のmessage変数を宣言
		 * <>の部分は、Listの中に入る型を設定しておく
		 */
		List<String> message = new ArrayList<String>();
		/*
		 *username変数内に空白がないか指定の文字数より少なかったり多かったりしてないかを確認する
		 */
		if (username.isEmpty()) {
			message.add("ユーザー名を入力してください");
		}else if (username.matches("[!-~]{3,16}")) {
			message.add("ユーザー名を正しく入力してください");
		}
		if (password.isEmpty()) {
			message.add("パスワードを入力してください");
		}else if (password.matches("[!-~]{6,32}")) {
			message.add("パスワードを正しく入力してください");
		}
		/*
		 *message変数にエラー文が入ってないか確認する
		 *List.size()でList型の変数内に何個の要素が入っているかを判別する
		 */
		System.out.println(message.size());
		if (message.size() == 0) {
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

				HttpSession session = request.getSession(false);

				if (session != null) {
					session.invalidate();
				}



			}catch (ClassNotFoundException e) {

				out.println("ClassNotFoundException:" + e.getMessage());

			}catch (SQLException e) {

				out.println("SQLException:" + e.getMessage());

			}catch (Exception e) {

				out.println("Exception:" + e.getMessage());

			}
		}else {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>sample1_singup</title>");
			out.println("<link rel=\"stylesheet\" href=\"/sample_jsp_servlet/css/bootstrap.css\"");
			out.println("</head>");
			out.println("<body>");
			out.println("<script src=\"/sample_jsp_servlet/js/bootstrap.min.js\"></script>");
			out.println("<div class=\"container\">");
			out.println("<h1>SIGNUP</h1>");
			message.forEach(msg -> {
				out.println("<p class=\"message-p\">" + msg + "</p>");
			});
			out.println("<form action=\"/sample_jsp_servlet/sample1/signup\" method=\"post\">");
			out.println("<div class=\"input-group mb-3\">");
			out.println("<span class=\"input-group-text\">USERNAME</span>");
			out.println("<input class=\"form-control\" type=\"text\" name=\"username\" placeholder=\"Username\" minlength=3 maxlength=16 required>");
			out.println("</div>");
			out.println("<div class=\"input-group mb-3\">");
			out.println("<span class=\"input-group-text\">PASSWORD</span>");
			out.println("<input class=\"form-control\" type=\"password\" name=\"password\" placeholder=\"Password\" minlength=6 maxlength=32 required>");
			out.println("</div>");
			out.println("<button class=\"btn btn-primary\" type=\"submit\">サインアップ</button>");
			out.println("<a href=\"/sample_jsp_servlet/sample1\"><button class=\"btn btn-secondary\" type=\"button\">キャンセル</button></a>");
			out.println("</form>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
		}
	}

}
