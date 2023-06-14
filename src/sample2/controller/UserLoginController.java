package sample2.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
/*
 *
 */
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/sample2/login")
public class UserLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserLoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		/*
		 *
		 */
		if (session != null) {
			session.invalidate();
		}
		request.getRequestDispatcher("/sample2/jsp/UserLoginController.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 *
		 */
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		/*
		 *
		 */
		if (username.isEmpty()) {

			request.setAttribute("message", "ユーザー名を入力してください");

		}else if (!username.matches("[!-~]{3,16}")) {

			request.setAttribute("message", "ユーザー名を正しく入力してください");

		}

		if (password.isEmpty()) {

			request.setAttribute("message", "パスワードを入力してください");

		}else if (!password.matches("[a-z]{6,32}")) {

			request.setAttribute("message", "パスワードを正しく入力してください");

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

					conn.close();
					pstmt.close();

					request.getRequestDispatcher("/sample2/jsp/Home.jsp").forward(request, response);;

				}else {

					request.setAttribute("message", "パスワードが間違っています");

					conn.close();
					pstmt.close();

					request.getRequestDispatcher("/sample2/jsp/UserLoginController.jsp").forward(request, response);;
				}

			}catch (ClassNotFoundException e) {

				e.printStackTrace();

			}catch (SQLException e) {

				e.printStackTrace();

			}catch (Exception e) {

				e.printStackTrace();

			}
		}else {

			request.getRequestDispatcher("/sample2/jsp/UserLoginController.jsp").forward(request, response);;

		}
	}

}
