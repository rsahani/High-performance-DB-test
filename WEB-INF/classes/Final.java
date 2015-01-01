//singelton class test
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class Final extends HttpServlet
{
// each instance of the class depicts a different user so for a given user this class is singelton
	private class Mp
	{
		Mp(String reqDesc,String parent)
		{
			this.reqDesc=reqDesc;
			this.parent=parent;
		}
		// add another class for sorted set for name and priority number
		private class Ss
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

		private String reqDesc;
		private String parent;
		private SortedSet<Ss> children;
		private Ss getChildByPriority(int p)
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
		private Ss getChildByName(String name)
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
			children.add(new Ss(name,p+1));//adding a new element
		}
		void alterPriority(SortedSet<Ss> subs,int n)
		{
			Iterator it=subs.iterator();
	        while(it.hasNext())
	        {
			    Ss value=(Ss)it.next();

			    value.setPriority(value.getPriority()+n);
	        }
		}
		void updateSs(SortedSet<Ss> subs,Ss tmp,int p)
		{
			subs.remove(tmp);
			tmp.setPriority(p);
			subs.add(tmp);	
		}
		//called by parent of the string where priority  needs to be changed
		void changeOrder(String name, int no)
		{
			// change priority of the given element
			
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
	        	;//object not found do something
		}
		void removeChild(String name)
		{
			Ss child=this.getChildByName(name);
			// System.out.println(children.last().getName());
			if(child!=children.last())
			{	
			Ss child1=this.getChildByPriority(child.getPriority()+1);
			SortedSet<Ss> tmp=children.tailSet(child1);
			alterPriority(tmp,-1);
				// System.out.println(children.last().getName());]
					// System.out.println(this.children);
			}	

			
				children.remove(child);
			if(children.isEmpty())
				children=null;
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
	}
	private static Final inst;
	private  static HashMap<String,Mp> table;
	private  static int reqno;
	private String reqid;
	public int a=30;
	
	Final()
	{
    	table=new HashMap<String,Mp>();
      	reqno=0;
      	Mp root=new Mp("root",null);
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
    
	//R0 is the root
	public boolean addElement(String rid,String desc,String par)
	{
		// if(parent==null)
		// 	return false;
		// reqid=rid;
		if(table.get(par)==null)
			return false;
		Mp tmp=new Mp(desc,par);
		table.put(rid,tmp);
		Mp parent=table.get(par);
		parent.addChild(rid);
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
		Mp parent=table.get(newpar);
		parent.addChild(reqid);
		tmp.setParent(newpar);
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
		return true;	
	}
	public boolean ifEist(String ele)
	{
		if(table.get(ele)!=null)
			return true;
		return false;
	}
}