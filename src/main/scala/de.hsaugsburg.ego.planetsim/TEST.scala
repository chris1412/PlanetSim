import java.awt.{ Color, Dimension, Font }
import javax.swing._
import javax.swing.border.LineBorder
import javax.swing.plaf.FontUIResource

object JavaSwingTest2 {
  def main(args: Array[String]) = {
    SwingUtilities.invokeLater {
      new Runnable {
        override def run() {
          init()
          MyFrame2.createAndShow()
        }
      }
    }
  }

  def init() = {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
    val defaultFont = new Font("メイリオ", Font.PLAIN, 14)
    for (key <- List("Label.font", "Button.font", "EditorPane.font"))
      UIManager.put(key, new FontUIResource(defaultFont))
  }
}

object MyFrame2 {
  def createAndShow(): Unit = {
    val frame = new MyFrame2
    frame.init()
    frame.pack()
    frame.setLocationRelativeTo(null) // 中央に表示
    frame.setVisible(true)
  }
}
class MyFrame2 private extends JFrame {
  private def init(): Unit = {
    setTitle("Java Swing Test 2")
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    setContentPane(new MyPanel2)
  }
}

class MyPanel2 extends JPanel {
  val lineBorder = new LineBorder(Color.RED, 2, true)
  val iconlabel = new JLabel {
    setIcon(new ImageIcon("1.png"))
    setPreferredSize(new Dimension(50, 50))
    setBorder(lineBorder)
  }
  val nameLabel = new JLabel {
    setText("<html><center>username<br>(ユーザー名)</center></html>")
    setHorizontalAlignment(SwingConstants.CENTER)
    setPreferredSize(new Dimension(100, 50))
    setBorder(lineBorder)
  }
  val editorPane = new JEditorPane {
    setContentType("text/html")
    setText("""<html><a href="http://java.sun.com/javase/ja/6/docs/ja/api/java/awt/GridBagLayout.html">GridBagLayout</a> クラスは、異なる大きさのコンポーネントでも縦横に、またはベースラインに沿って配置できる柔軟なレイアウトマネージャーです。<br>それぞれの GridBagLayout オブジェクトは、セルによって構成される動的な矩形グリッドを格納しています。</html>""")
    setEditable(false)
    putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true)
    setPreferredSize(new Dimension(500, 95))
    setBorder(lineBorder)
  }
  val editorPaneScroll = new JScrollPane(editorPane) {
    setPreferredSize(editorPane.getPreferredSize)
  }
  val timeLabel = new JLabel {
    setText("<html><center>12/03<br>11:12</center></html>")
    setHorizontalAlignment(SwingConstants.CENTER)
    setPreferredSize(new Dimension(50, 50))
    setBorder(lineBorder)
  }
  addComponents()

  private def addComponents(): Unit = {
    import java.awt.{ Component, GridBagConstraints, GridBagLayout }
    import java.awt.GridBagConstraints._
    val gbl = new GridBagLayout
    setLayout(gbl)

    def addComponent(component: Component, x: Int, y: Int, width: Int, height: Int,
                     weightx: Int, weighty: Int, anchor: Int, fill: Int): Unit = {
      val gbc = new GridBagConstraints
      gbc.gridx      = x
      gbc.gridy      = y
      gbc.gridwidth  = width
      gbc.gridheight = height
      gbc.weightx    = weightx
      gbc.weighty    = weighty
      gbc.anchor     = anchor
      gbc.fill       = fill
      gbl.setConstraints(component, gbc)
      add(component)
    }

    addComponent(iconlabel,        0, 0, 1, 1, 0, 1, NORTH,  VERTICAL)
    addComponent(nameLabel,        1, 0, 1, 1, 0, 1, CENTER, VERTICAL)
    addComponent(editorPaneScroll, 2, 0, 1, 1, 1, 1, CENTER, BOTH)
    addComponent(timeLabel,        3, 0, 1, 1, 0, 1, CENTER, VERTICAL)
  }
}