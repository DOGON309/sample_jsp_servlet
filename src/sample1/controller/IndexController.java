package sample1.controller;

/*
 * import java.io.*; - ライブラリの読み込み
 * これを読み込むことによってPrintWriter等が使えるようになる
 */
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sample1")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public IndexController() {
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
		out.println("<title>sample1_index</title>");
		out.println("<link rel=\"stylesheet\" href=\"/sample_jsp_servlet/css/bootstrap.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<script src=\"\sample_jsp_servlet/js/bootstrap.min.js\"></script>");
		out.println("<div class=\"container\">");
		out.println("<h1>SAMPLE1</h1>");
		out.println("<a href=\"/sample_jsp_servlet/sample1/login\"><button class=\"btn btn-primary\">LOGIN</button></a>");
		out.println("<a href=\"/sample_jsp_servlet/sample1/signup\"><button class=\"btn btn-light\">LOGIN</button></a>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}
