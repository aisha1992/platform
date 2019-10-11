package aisha.service;



import aisha.bean.Talent;

 
public interface TalentService {
 
    public long addTalent(Talent talent);
    public Talent listTalents(Talent talent);
    public Talent getTalent(int id);
    public void updateTalent(Talent talent);    
    public Talent listBeansCustom(Talent talent);
 
}
