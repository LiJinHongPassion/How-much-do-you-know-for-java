## 简述

#### **代码一**

> ```java
> Map<String, Object> map = new HashMap<String, Object>();
> map.put("k1", "v1");
> System.out.println(map);
> //结果
> {k1=v1}
> ```
>
> 上述代码是我们常用的`HashMap`的方式, 以`String`作为`key`, `Object`作为`value`
> 我们在使用`HashMap`的时候, 一般都没有注意关于`HashMap`的`equals`和`hashCode`的重写问题

#### **代码二**

**问题来了, 首先**

> ```java
> Map<String, Object> map = new HashMap<String, Object>();
> String k1 = new String("key");
> String k2 = new String("key");
> System.out.println(k1 == k2);
> map.put( k1, "v1");
> System.out.println(map);
> map.put( k2, "v2");
> 
> System.out.println(map);
> //结果
> false
> {key=v1}
> {key=v2}
> ```
>
> `==`是用于基本数据类型的值比较, 引用类型的地址比较, 在这里明显`k1`和`k2`是同不是一个`String`对象, 但是他们的值都是`key`, 所以按照常理来说利用`k2`也能改变`k1`的值
>
> 结果显示的确能够利用`k2`改变`k1`

#### **代码三**

**但是**

> ```java
> public class Test1 {
>     
>     public static void main(String[] args) {
> 
>         Map<CodeAntKey, Object> map = new HashMap<CodeAntKey, Object>();
>         CodeAntKey k1 = new CodeAntKey("key");
>         CodeAntKey k2 = new CodeAntKey("key");
>         System.out.println(k1 == k2);
>         map.put( k1, "v1");
>         System.out.println(map);
>         map.put( k2, "v2");
>         System.out.println(map);
>     }
> }
> class CodeAntKey{
>     private String key;
> 
>     public CodeAntKey(String key) {
>         this.key = key;
>     }
> }
> //结果
> false
> {com.company.CodeAntKey@1b6d3586=v1}
> {com.company.CodeAntKey@4554617c=v2, com.company.CodeAntKey@1b6d3586=v1}
> ```
>
> 这里我自定义了一个类, 利用相同的属性`new`了两个对象, 所以这里他们不是同一个对象, 所以为`fasle`
>
> 但是按照上面`String`的示例来说, 应该`k2`能够改变`k1`的值, 但是这里为什么失败了？因为没有重写`equals`和`hashCode`方法

#### **代码四**

**怎样解决**

>```java
>public class Test1 {
>
>    public static void main(String[] args) {
>
>        Map<CodeAntKey, Object> map = new HashMap<CodeAntKey, Object>();
>        CodeAntKey k1 = new CodeAntKey("key");
>        CodeAntKey k2 = new CodeAntKey("key");
>        System.out.println(k1 == k2);
>        map.put( k1, "v1");
>        System.out.println(map);
>        map.put( k2, "v2");
>        System.out.println(map);
>    }
>}
>class CodeAntKey{
>    private String key;
>
>    public CodeAntKey(String key) {
>        this.key = key;
>    }
>
>    @Override
>    public boolean equals(Object o) {
>        if (this == o) return true;
>        if (!(o instanceof CodeAntKey)) return false;
>        CodeAntKey that = (CodeAntKey) o;
>        return key.equals(that.key);
>    }
>
>    @Override
>    public int hashCode() {
>        return Objects.hash(key);
>    }
>}
>//结果
>false
>{com.company.CodeAntKey@19e7e=v1}
>{com.company.CodeAntKey@19e7e=v2}
>```
>
>这里的代码跟上面的代码就多了`equals`和`hashCode`方法, 然后发现`k2`可以改变`k1`的值了

**那到底是为什么呢, 下面慢慢道来...**

---

## HashMap的存储方式 - PUT

> 在面试的时候经常问这个问题, 大家都是一通`jdk1.7之前是数组 + 链表`和`jdk1.8是数组 + 链表 + 红黑树( 链表长度超过8就使用红黑树 )`这样说, 其实也是没有错的

**但是HashMap是怎样将数据放至进入他的数据结构中的呢 ? **

**先放部分源码, 有兴趣的可以阅读put全部源码的可以去https://www.cnblogs.com/captainad/p/10905184.html**

也就是说`hashMap`在`put`的时候是

1. 需要先计算`key`的`hash`值( `hash(key)` ), 
2. 然后利用`hash值`去寻址, 
3. 当地址上已经存在内容, 再利用`equals`比较对象的;

自定义类在没有重写`hashCode`的方法时, 默认调用的是Object类的`equals()`和`hashCode()`, 

> ```java
> public V put(K key, V value) {
> 
>  return putVal(hash(key), key, value, false, true);
> } 
> 
> //计算key的hash值
> static final int hash(Object key) {
>  int h;
>  return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
> }
> 
> //int hash：key的hash值
> final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
>              boolean evict) {
>   Node<K,V>[] tab; Node<K,V> p; int n, i;
>   // 如果map还是空的，则先开始初始化，table是map中用于存放索引的表
>   if ((tab = table) == null || (n = tab.length) == 0) {
>       n = (tab = resize()).length;
>   }
>   // 使用hash与数组长度减一的值进行异或得到分散的数组下标，预示着按照计算现在的
>   // key会存放到这个位置上，如果这个位置上没有值，那么直接新建k-v节点存放
>   // 其中长度n是一个2的幂次数
>   if ((p = tab[i = (n - 1) & hash]) == null) {
>       tab[i] = newNode(hash, key, value, null);
>   }
> 
>   // 如果走到else这一步，说明key索引到的数组位置上已经存在内容，即出现了碰撞
>   // 这个时候需要更为复杂处理碰撞的方式来处理，如链表和树
>   else {
>       Node<K,V> e; K k;
>       // 其中p已经在上面通过计算索引找到了，即发生碰撞那一个节点
>       // 比较，如果该节点的hash和当前的hash相等，而且key也相等或者
>       // 在key不等于null的情况下key的内容也相等，则说明两个key是
>       // 一样的，则将当前节点p用临时节点e保存
>       if (p.hash == hash &&
>               ((k = p.key) == key || (key != null && key.equals(k)))) {
>           e = p;
>       }else if(){
>           //...省略
>       }else{
>           //...省略
>       }
> 
>       // 此时的e是保存的被碰撞的那个节点，即老节点
>       if (e != null) { // existing mapping for key
>           V oldValue = e.value;
>           // onlyIfAbsent是方法的调用参数，表示是否替换已存在的值，
>           // 在默认的put方法中这个值是false，所以这里会用新值替换旧值
>           if (!onlyIfAbsent || oldValue == null)
>               e.value = value;
>           // Callbacks to allow LinkedHashMap post-actions
>           afterNodeAccess(e);
>           return oldValue;
>       }
>   }
>   //...省略
> }
> ```

**`Object`中的`equals()`和`hashCode()`**

>```java
>public native int hashCode();
>
>public boolean equals(Object obj) {
>    	return (this == obj);
>}
>```
>

所以我们需要重写`equals()`和`hashCode()`方法, 不然就会造成**代码四**标题的问题

## HashMap的获取方式 - GET

<font size="6px" color="red">为什么在重写equals方法的同时，必须重写hashCode方法</font>

**上源码**

```java
public V get(Object key) {
    Node<K,V> e;
    return (e = getNode(hash(key), key)) == null ? null : e.value;
}

final Node<K,V> getNode(int hash, Object key) {
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    //确保table不为空，并且计算得到的下标对应table的位置上有节点
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (first = tab[(n - 1) & hash]) != null) {
        //判断第一个节点是不是要找的key
        if (first.hash == hash && // always check first node
            ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
        //如果第一个节点就查找链表或者红黑树
        if ((e = first.next) != null) {
            //红黑树上查找
            if (first instanceof TreeNode)
                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
            do {
                //链表上查找
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    return null;
}
```

当我们试图添加或者找到一个`key`的时候，方法会去判断哈希值是否相等和值是否相等，都相等的时候才会判断这个`key`就是要获取的`key`。也就是说，严格意义上，一个`HashMap`里是不允许出现相同的`key`的。

当我们使用对象作为`key`的时候，根据原本的`hashCode`和`equals`仍然能保证`key`的唯一性。但是当我们重写了`equals`方法而不重写`hashCode()`方法时，**可能出现值相等但是因为地址不相等导致哈希值不同，最后导致出现两个相同的`key`的情况**。（注：Java的HashCode的生成与对象的内存地址有关）

```java
public class Test1 {

    public static void main(String[] args) {

        Map<CodeAnimal, Object> map = new HashMap<CodeAnimal, Object>();
        CodeAnimal k1 = new CodeAnimal("ant", 25);
        CodeAnimal k2 = new CodeAnimal("ant", 25);

        System.out.println("k1 == k2 : " + (k1 == k2));
        map.put( k1, "v1");
        System.out.println("1 ---- " + map);
        map.put( k2, "v2");
        System.out.println("2 ---- " + map);

        System.out.println(k1.hashCode());
        System.out.println(k2.hashCode());
    }
}
class CodeAnimal{
    private String name;
    private Integer age;
    public String getName() {
        return name;
    }
    public Integer getAge() {
        return age;
    }
    public CodeAnimal(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodeAnimal)) return false;
        CodeAnimal that = (CodeAnimal) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getAge(), that.getAge());
    }
}

//结果
k1 == k2 : false
1 ---- {com.company.CodeAnimal@1b6d3586=v1}
2 ---- {com.company.CodeAnimal@4554617c=v2, com.company.CodeAnimal@1b6d3586=v1}
460141958
1163157884
```

上述代码得出, 在两个对象的值相同时, 两个对象分别存入`map`, 如果不重写`hashCode`方法 ,就会存在两个`key`相同

>如果调用equals方法得到的结果为true，则两个对象的hashcode值必定相等；
>
>如果equals方法得到的结果为false，则两个对象的hashcode值有可能不同；
>
>如果两个对象的hashcode值不等，则equals方法得到的结果必定为false；
>
>如果两个对象的hashcode值相等，则equals方法得到的结果未知。

## 参考文献

[HashMap之put方法流程解读](https://www.cnblogs.com/captainad/p/10905184.html)