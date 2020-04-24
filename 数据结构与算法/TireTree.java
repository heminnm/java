import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class TreeNode{
    final static int MAX_SIZE = 26;
    char data;		//表示当前节点存的字母
    boolean isEnd = false;		//表示是否为叶子节点
    TreeNode [] childs;		//表示子节点

    public TreeNode(){
        childs = new TreeNode[MAX_SIZE];		//因为英文最多26个字母
        isEnd = false;
    }
}
public class TireTree {
    //redis 同时只会执行一条命令.inc 0 ++, 释放 setnx set 一个设置失效时间. lua zookeeper 节点一定有序 TCC MQ
    public static void createTireTree(TreeNode node,String str){	//单词全部转成小写
        //ascii A => 65,a=>97 -97 >0
        //a->0 b->1,c->2
        char d[] = str.toCharArray();
        for(int i = 0 ;i < d.length; i++){
            int loc = d[i] - 'a';		//转成0~25之间的数字了 这里是一个技巧
            if(node.childs[loc]  == null){//我们把英文字母存到一个数组里面0~25  a['a'] === a[97] => a[0] = 'a'缩小空间，0+97
                node.childs[loc] = new TreeNode();
                node.childs[loc].data = d[i];
            }
            node = node.childs[loc];
        }
        node.isEnd = true;
    }
    public static boolean find(String str,TreeNode node){

        char d[] = str.toCharArray();
        for(int  i = 0 ; i < d.length ; i ++) {
            int loc = d[i] - 'a';
            if(node.childs[loc] != null){
                node = node.childs[loc];
            }else{
                return false;
            }
        }//O(Nlog(n))		O(n)
        return node.isEnd;
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String s[] = {"java","ps","php","ui","css","js"};
        TreeNode root = new TreeNode();
        for(String ss : s){
            createTireTree(root,ss);
        }
        System.out.println("插入完成");
        System.out.println(find("java", root));
        System.out.println(find("jav", root));	//找前缀就是自动补全
    }
}
