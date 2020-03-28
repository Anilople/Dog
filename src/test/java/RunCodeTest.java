import com.github.anilople.dog.DogLanguage;
import com.github.anilople.dog.backend.constant.PathNameConstant;
import com.github.anilople.dog.backend.runtime.environment.LocalCodeLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 运行在src/test/dog下的测试代码
 */
class RunCodeTest {

  /**
   * 测试代码所在的路径
   */
  public static final String TEST_CODE_DIR = "src/test/dog";

  /**
   * 库函数所在的路径
   * @see PathNameConstant#CODE_LIBRARY_ROOT
   */
  public static final String LIBRARY_DIR = "src/main/dog";

  @BeforeAll
  static void beforeAll() {
    // 设置库函数路径
    System.setProperty(PathNameConstant.CODE_LIBRARY_ROOT, LIBRARY_DIR);
    // 触发重新载入
    LocalCodeLoader.getDefaultInstance().reload();
  }

  @Test
  void run() throws IOException {
    Path testCodes = Paths.get(TEST_CODE_DIR);
    Files.walk(testCodes)
    .forEach(
        path -> {
          System.out.println("=>运行" + path);
          if (!Files.isDirectory(path)) {
            try {
              DogLanguage.execute(path);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          System.out.println();
          System.out.println("<=结束运行" + path);
        }
    );
  }
}
