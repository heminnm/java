/**
 *单向链表
 */
public class LinkList {
    Node head = null; // 头节点
    /**
     * 链表中的节点，data代表节点的值，next是指向下一个节点的引用
     */
    class Node {
        Node next = null;// 节点的引用，指向下一个节点
        int data;// 节点的对象，即内容

        public Node(int data) {
            this.data = data;
        }
    }

    /**
     * 向链表中插入数据
     *
     * @param d
     */
    public void addNode(int d) {

        if(null == head){
            head=new Node(d);;  return;
        }
        Node temp=head;
        while (temp.next != null) temp=temp.next;
        temp.next=new Node(d);;
    }

    /**
     *
     * @param index:删除第index个节点
     * @return
     *
     */


    /**
     * o(n)时间 删除节点 按照索引位置删除节点
     *
     *
     * @return 返回新的链表(新链表，头结点引用)
     *
     */
    public static Node removeNode(Node head, int index) {
        int i=1;
        Node preNode=head;
        Node curNode=head.next;
        while ( curNode.next !=null ){
            i++;
            if( i==index ) preNode.next=curNode.next;
            preNode=preNode.next;
            curNode=curNode.next;
        }
        return head;
    }

    /**
     *
     * @return 返回节点长度
     */
    public int length() {
        int length = 0;
        Node tmp = head;
        while (tmp != null) {
            length++;
            tmp = tmp.next;
        }
        return length;
    }

    /**
     * 在不知道头指针的情况下删除指定节点
     *
     * @param n
     * @return
     */
    public boolean deleteNode11(Node n) {
        if (n == null || n.next == null) {
            return false;
        }
        int tmp = n.data;
        n.data = n.next.data;
        n.next.data = tmp;
        n.next = n.next.next;
        System.out.println("删除成功！");
        return true;
    }

    public void printList() {
        Node tmp = head;
        while (tmp != null) {
            System.out.println(tmp.data);
            tmp = tmp.next;
        }
    }

    /**
     * 链表反转
     *
     * @param head
     * @return
     */
    public  Node ReverseIteratively(Node head) {
        Node pReversedHead = head;
        Node pNode = head;
        Node pPrev = null;
        while (pNode != null) {
            Node pNext = pNode.next;
            if (pNext == null) {
                pReversedHead = pNode;
            }
            pNode.next = pPrev;
            pPrev = pNode;
            pNode = pNext;
        }
        this.head = pReversedHead;
        return this.head;
    }

    public static void main(String[] args) {
        LinkList list = new LinkList();
        list.addNode(5);
        list.addNode(3);
        list.addNode(1);
        System.out.println("linkLength:" + list.length());
        System.out.println("head.data:" + list.head.data);
        list.printList();
   //     removeNode(list.head,2);
        System.out.println("After deleteNode(4):");
        //list.ReverseIteratively(list.head);
        list.printList();
    }


}