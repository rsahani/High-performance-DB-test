//singelton class test
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.json.simple.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class Final extends HttpServlet
{
// each instance of the class depicts a different user so for a given user this class is singelton
			public class Ss
		{
			private String name;
			private int priority;
			Ss(String name,int priority)
			{
				this.name=name;
				this.priority=priority;
			}
			public int getPriority()
			{
				return this.priority;
			}
			public void setPriority(int p)
			{
				this.priority=p;
			}
			public String getName()
			{
				return this.name;
			}

		}
	private class Mp
	{
		Mp(String rid,String reqDesc,String parent)
		{
			this.selfid=rid;
			this.reqDesc=reqDesc;
			this.parent=parent;
			this.totalchild=0;;
		}
		// add another class for sorted set for name and priority number
		private class MyComp implements Comparator<Ss>
	    {
	        public int compare(Ss s1, Ss s2)
	        {
	            if(s2.getPriority()!=s1.getPriority())
	            	return s1.getPriority() - s2.getPriority();
	            else
	            	return s1.getName().compareTo(s2.getName());
	        }
	    }
	    private String selfid;
		private String reqDesc;
		private String parent;
		private int totalchild;
		private SortedSet<Ss> children;
		public Ss getChildByPriority(int p)
		{
			Iterator it=children.iterator();
	        
	        Ss value;
	        while(it.hasNext())
	        {
			    value=(Ss)it.next();
			    if(p==value.getPriority())
				    return value;
	        }
	        return null;
	    }
		public Ss getChildByName(String name)
		{
			Iterator it=children.iterator();
	        
	        Ss value;
	        while(it.hasNext())
	        {
			    value=(Ss)it.next();
			    if(name.equals(value.getName()))
				    return value;
	        }
	        return null;
		}

		// assming we can only add to the botom of the set and then move it later wherever we like
		void addChild(String name)
		{//add to the sorted set and reorder according to the priority
			//add code for checking of children null or not and adding it
			// Redisson red=redisson.create();
				int p;
			if(children==null)
				children=new TreeSet<Ss>(new MyComp());
			try
			{
				Ss tmp=children.last();
				p=tmp.getPriority();
			}
			catch(NoSuchElementException e)
			{
				p=0;
			}
			// children.add(new Ss(name,p+1));//adding a new element
			// System.out.println(name);
			// System.out.println(p);
			children.add(new Ss(name,p+1));//adding a new element
		}
		void alterPriority(SortedSet<Ss> subs,int n)
		{
			Iterator it=subs.iterator();
	        while(it.hasNext())
	        {
			    Ss value=(Ss)it.next();

			    // updateSs(subs,value,n);

			    value.setPriority(value.getPriority()+n);
	        }	
		}
		//called by parent of the string where priority  needs to be changed
		void updateSs(SortedSet<Ss> subs,Ss tmp,int p)
		{
			subs.remove(tmp);
			tmp.setPriority(p);
			subs.add(tmp);	
		}
		void changeOrder(String name, int no)
		{
			// change priority of the given element
			 // System.out.println("before");
			 // this.printchildren();
			
			// System.out.println(children.first().name);
			Ss oldpos=this.getChildByName(name);
			Ss newpos=this.getChildByPriority(no);
			// System.out.println(oldpos.name);
			// System.out.println(newpos.name);
			
			if(oldpos.getPriority()>newpos.getPriority())
	        {	
	        	// System.out.println("here1");
	        	SortedSet<Ss> tmp=children.subSet(newpos,oldpos);
	        	alterPriority(tmp,1);
	        }
	        else
	        {
	        	SortedSet<Ss> tmp;
	        	// System.out.println("here2");
	        	if(newpos==children.last())
	        		tmp=children.tailSet(oldpos);	
	        	else{
	        	Ss newp=this.getChildByPriority(newpos.getPriority()+1);
	        	 tmp=children.subSet(oldpos,newp);
	        	
	        	}		
	        	alterPriority(tmp,-1);	
	        }
	        updateSs(children,oldpos,no);
	        // if(v==null)
	   		//      System.out.println("after");
			 // this.printchildren();
	        	;//object not found do something
		}
		void printchildren()
		{
			if(children==null)
				return;
			Iterator it=children.iterator();
	        
	        Ss value;
	        while(it.hasNext())
	        {
			    value=(Ss)it.next();
			    System.out.println(value.getName()+" "+value.getPriority());
	        }
		}
		void removeChild(String name)
		{
			Ss child=this.getChildByName(name);
			// System.out.println("Before");	
			// this.printchildren();
			// System.out.println(children.last().getName());
			if(child!=children.last())
			{	
				// System.out.println(child.getName());
				// System.out.println(child.getPriority());
			Ss child1=this.getChildByPriority(child.getPriority()+1);
			SortedSet<Ss> tmp=children.tailSet(child1);
			alterPriority(tmp,-1);
					// System.out.println(this.children);
			}	

			
				children.remove(child);
			if(children.isEmpty())
				children=null;
			// System.out.println("After");	
			// this.printchildren();
			
			
			//remove and (change priority CHECK if nesecarry) of all cases below this
		}
		String getParent()
		{
			return parent;
		}
		void setParent(String par)
		{
			this.parent=par;
		}
		SortedSet<Ss> getChildren()
		{
			return children;
		}
		void printChildren()
		{
			Iterator it=children.iterator();
	        while(it.hasNext())
	        {
			    Ss value=(Ss)it.next();
			    System.out.println(value.getName());
			    System.out.println(value.getPriority());
			    
	        }	
		}
	}
	// private class MyCom implements Comparator<Mp>
	//     {
	//         public int compare(Mp m1, Mp m2)
	//         {
	            
	//             return m1.prior-m12.prior;
	//             // else
	//             // 	return s1.getName().compareTo(s2.getName());
	//         }
	//     }
	private static Final inst;
	private static HashMap<String,Mp> table;
	private static int reqno;
	private String reqid;
	private static int a=0;
	Final()
	{
    	table=new HashMap<String,Mp>();
    	reqno=0;
      	Mp root=new Mp("R0","root",null);
      	table.put("R0",root);
  	}
	public void init() throws ServletException
    {
      // inst=new Final();
    }
    public static Final getInstance()
    {
    	if(inst!=null)
        	return inst;
        inst=new Final();
        return inst;
    }
    
 private void updateTc(String reqid,int n)
    {
    	Mp par=table.get(reqid);
		//int f1=0;
			// System.out.println(par.getParent()==null);

		while(par.getParent()!=null)
		{
			par.totalchild+=n;
			par=table.get(par.getParent());	
		}
		par.totalchild+=n;
    }
	//R0 is the root
	public boolean addElement(String rid,String desc,String par)
	{
		// if(parent==null)
		// 	return false;
		// reqid=rid;
		if(table.get(par)==null)
			return false;
		Mp tmp=new Mp(rid,desc,par);
		table.put(rid,tmp);
		Mp parent=table.get(par);
		parent.addChild(rid);
		updateTc(par,1);
		reqno++;
		return true;
		//adding it to the children set of the parent

	}
	public void printElements()
	{
		System.out.println(this.table);
	}
	public boolean moveElement(String rid,String newpar)
	{
		this.reqid=rid;
		boolean cycle=this.checkCycle(newpar);
		if(cycle)
			return false;
		Mp tmp=table.get(this.reqid);
		Mp currpar=table.get(tmp.parent);
		currpar.removeChild(this.reqid);
		updateTc(currpar.selfid,-1);
		Mp parent=table.get(newpar);
		parent.addChild(reqid);
		tmp.setParent(newpar);
		updateTc(parent.selfid,1);
	//check for cycles and if no cycle exists move it to the new locations end 
		// change its priority accordingly not done
		return true;
	}
	private boolean checkCycle(String newpar)
	{
		// String this.reqid; WhY did i use it
				// move up the table and if thiss.reqid is found say a cycle exists
			// move up the table and if thiss.reqid is found say a cycle exists
		Mp par=table.get(newpar);
		Final f=this;
		int f1=0;
			// System.out.println(par.getParent()==null);

		while(par.getParent()!=null&&(!par.getParent().equals(this.reqid)||!par.getParent().equals("R0")))
		{
			if(par.getParent().equals(this.reqid))	
			{	
				f1=1;
				break;
			}
			par=table.get(par.getParent());
				
		}
		if(f1==1)
			return true;
		return false;

	}
	public boolean changePriority(int newp,String reqid)
	{
		if(reqid=="R0")
			return false;
		Mp curr=table.get(reqid);
		Mp par=table.get(curr.getParent());
		int s=par.children.size();
		if(newp<=0||newp>s)
			return false;

		par.changeOrder(reqid,newp);
		return true;	
	}

	// deleted only when its child is empty CHECK maybe work need sot be done
	public boolean removeElement(String req) 
	{
		if(req=="R0")
			return false;
		Mp tmp=table.get(req);
		Mp currpar=table.get(tmp.parent);
		if(tmp.getChildren()!=null)
			return false;
		currpar.removeChild(req);
		table.remove(req);
		updateTc(currpar.selfid,-1);	
		return true;	
	}
	private boolean isRootsChild(String rid)
	{
		Mp tmp=table.get(rid);
		// System.out.println(rid);
		// System.out.println(tmp.parent);
		// System.out.println(tmp.parent.equals("R0"));
		if(tmp.parent.equals("R0"))
			return true;
		return false;
	}
	private JSONArray getArr(JSONArray ja,String rid)
	{
		int f=0;//f=0 same level f=1 level change
		int c=0;//children left at the current level
		Mp curr=table.get(rid);
		if(a==20||curr==null)
			return ja;
		JSONObject jo=new JSONObject();
		String p=curr.parent;
		// System.out.println("Adding "+curr.selfid+" with parent as "+curr.parent);
		if(curr.parent.equals("R0"))	
			jo.put("parent","#");
		else
			jo.put("parent",p);
		jo.put("text",rid);
		jo.put("id",rid);
		ja.add(jo);
		Final.a++;
		// if(curr.totalchild>20-a)
		// {
	
		// 	Iterator it=curr.children.iterator();
	
	 //        Ss value;
	 //        while(it.hasNext())
	 //        {
		// 	    value=(Ss)it.next();
		// 	    ja=getArr(ja,value.name);
	 //        }
		//     return ja;
		// }
		// else
		// {
			if(curr.children==null)
			{
				// System.out.println(curr.selfid);
				Mp pr=table.get(p);
				while(1==1)
				{
					if(pr==null)
						break;
					Ss tmp=pr.getChildByName(curr.selfid);
					Ss tmp1=pr.getChildByPriority(tmp.priority+1);
					if(tmp1==null)
					{
						curr=pr;
						pr=table.get(pr.parent);
					}
					else
					{
						curr=table.get(tmp1.name);
						break;
					}

				}
				if(pr!=null)
				{
					ja=getArr(ja,curr.selfid);	
				}
				return ja;
			}
			
			 ja=getArr(ja,curr.children.first().name);
	        
			return ja;

		// }

	}
	public JSONArray getElements(String rid)
	{
		// System.out.println(rid);
		// System.out.println(isRootsChild(rid));
		if(!isRootsChild(rid))
			return null ;
		Mp tmp=table.get(rid);
		// System.out.println(rid);
		JSONArray ja=new JSONArray();
		ja=getArr(ja,rid);
		Final.a=0;
		return ja;

		// System.out.println(ja);

	}
	public boolean ifEist(String ele)
	{
		if(table.get(ele)!=null)
			return true;
		return false;
	}
}