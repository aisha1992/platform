package aisha.dao;

import aisha.bean.Talent;


 
public interface BasicDAO {
 
    public long addTalent(Talent talent);
    public Talent getTalent(int id);
    public void updateTalent(Talent talent);		
	public Talent listBeans(Talent talent);
     
}