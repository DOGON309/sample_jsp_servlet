package sample1.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
/*
 *
 */
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/sample1/login")
public class UserLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserLoginController() {
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
		 *
		 */
		HttpSession session = request.getSession(false);
		/*
		 *
		 */
		if (session != null) {
			session.invalidate();
		}
		/*
		 * --Java
		 * out - PrintWriterクラスが入ってる変数
		 * Println - PrintWriterクラスに入ってるprintln()関数 … System.out.println()とは違うよ…
		 */
		out.println("<html>");
		out.println("<head>");
		out.println("<title>sample1_login</title>");
		out.println("<link rel=\"stylesheet\" href=\"/sample_jsp_servlet/css/bootstrap.css\"");
		out.println("</head>");
		out.println("<body>");
		out.println("<script src=\"/sample_jsp_servlet/js/bootstrap.min.js\"></script>");
		out.println("<div class=\"container\">");
		out.println("<h1>LOGIN</h1>");
		out.println("<form action=\"/sample_jsp_servlet/sample1/login\" method=\"post\">");
		out.println("<div class=\"input-group mb-3\">");
		out.println("<span class=\"input-group-text\">USERNAME</span>");
		out.println("<input class=\"form-control\" type=\"text\" name=\"username\" placeholder=\"Username\" minlength=3 maxlength=16 required>");
		out.println("</div>");
		out.println("<div class=\"input-group mb-3\">");
		out.println("<span class=\"input-group-text\">PASSWORD</span>");
		out.println("<input class=\"form-control\" type=\"password\" name=\"password\" placeholder=\"Password\" minlength=6 maxlength=32 required>");
		out.println("</div>");
		out.println("<button class=\"btn btn-primary\" type=\"submit\">ログイン</button>");
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
		 *
		 */
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		/*
		 *
		 */
		List<String> message = new ArrayList<String>();
		/*
		 *
		 */
		if (username.isEmpty()) {
			message.add("ユーザー名を入力してください");
		}else if (!username.matches("[!-~]{3,16}")) {
			message.add("ユーザー名を正しく入力してください");
		}
		if (password.isEmpty()) {
			message.add("パスワードを入力してください");
		}else if (!password.matches("[a-z]{6,32}")) {
			message.add("パスワードを正しく入力してください");
		}
		/*
		 *
		 */
		if (!username.isEmpty() || !password.isEmpty()) {
			/*
			 * SQLとのセッションを始める時にエラーが出ていないか判別するために
			 * 変数を宣言してあとから判別できるようにしておく
			 */
			Connection conn = null;
			/*
			 *
			 */
			ResultSet rs = null;
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
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM account WHERE username LIKE ?");
				/*
				 *
				 */
				pstmt.setString(1, username);
				/*
				 *
				 */
				rs = pstmt.executeQuery();
				/*
				 *
				 */
				rs.next();
				/*
				 *
				 */
				if (password.equals(rs.getString("password"))) {

					HttpSession session = request.getSession(true);

					session.setAttribute("userid", rs.getInt("id"));

					out.println("<html>");
					out.println("<head>");
					out.println("<title>sample1_home</title>");
					out.println("<link rel=\"stylesheet\" href=\"css/bootstrap.css\">");
					out.println("</head>");
					out.println("<body>");
					out.println("<script src=\"js/bootstrap.min.js\"></script>");
					out.println("<h2>HOME</h2>");
					out.println("</body>");
					out.println("</html>");

				}else {

					message.add("パスワードが間違っています");

					out.println("<html>");
					out.println("<head>");
					out.println("<title>sample1_login</title>");
					out.println("<link rel=\"stylesheet\" href=\"/sample_jsp_servlet/css/bootstrap.css\"");
					out.println("</head>");
					out.println("<body>");
					out.println("<script src=\"/sample_jsp_servlet/js/bootstrap.min.js\"></script>");
					out.println("<div class=\"container\">");
					out.println("<h1>LOGIN</h1>");
					message.forEach(msg -> {
						out.println("<p class=\"message-p\">" + msg + "</p>");
					});
					out.println("<form action=\"/sample_jsp_servlet/sample1/login\" method=\"post\">");
					out.println("<div class=\"input-group mb-3\">");
					out.println("<span class=\"input-group-text\">USERNAME</span>");
					out.println("<input class=\"form-control\" type=\"text\" name=\"username\" placeholder=\"Username\" minlength=3 maxlength=16 required>");
					out.println("</div>");
					out.println("<div class=\"input-group mb-3\">");
					out.println("<span class=\"input-group-text\">PASSWORD</span>");
					out.println("<input class=\"form-control\" type=\"password\" name=\"password\" placeholder=\"Password\" minlength=6 maxlength=32 required>");
					out.println("</div>");
					out.println("<button class=\"btn btn-primary\" type=\"submit\">ログイン</button>");
					out.println("<a href=\"/sample_jsp_servlet/sample1\"><button class=\"btn btn-secondary\" type=\"button\">キャンセル</button></a>");
					out.println("</form>");
					out.println("</div>");
					out.println("</body>");
					out.println("</html>");

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
			out.println("<title>sample1_login</title>");
			out.println("<link rel=\"stylesheet\" href=\"/sample_jsp_servlet/css/bootstrap.css\"");
			out.println("</head>");
			out.println("<body>");
			out.println("<script src=\"/sample_jsp_servlet/js/bootstrap.min.js\"></script>");
			out.println("<div class=\"container\">");
			out.println("<h1>LOGIN</h1>");
			message.forEach(msg -> {
				out.println("<p class=\"message-p\">" + msg + "</p>");
			});
			out.println("<form action=\"/sample_jsp_servlet/sample1/login\" method=\"post\">");
			out.println("<div class=\"input-group mb-3\">");
			out.println("<span class=\"input-group-text\">USERNAME</span>");
			out.println("<input class=\"form-control\" type=\"text\" name=\"username\" placeholder=\"Username\" minlength=3 maxlength=16 required>");
			out.println("</div>");
			out.println("<div class=\"input-group mb-3\">");
			out.println("<span class=\"input-group-text\">PASSWORD</span>");
			out.println("<input class=\"form-control\" type=\"password\" name=\"password\" placeholder=\"Password\" minlength=6 maxlength=32 required>");
			out.println("</div>");
			out.println("<button class=\"btn btn-primary\" type=\"submit\">ログイン</button>");
			out.println("<a href=\"/sample_jsp_servlet/sample1\"><button class=\"btn btn-secondary\" type=\"button\">キャンセル</button></a>");
			out.println("</form>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
		}
	}

}
