package articulation_point_in_a_graphe;
public class Graph {

    public boolean arc[][] = new boolean[100][100];
    public boolean noeud[] = new boolean[100] ;

    public  void dfs(int root , boolean[] noeud ,boolean[][] arc , int ignore , int compteur)
    {
        noeud[root]=true;
        for ( int i=0 ; i<compteur ; i++ )
            if (i!=ignore)
                if ( (i!=root)&&(arc[root][i]==true) )
                {
                    if (noeud[i]== false ) dfs(i,noeud,arc ,ignore,compteur ) ;
                }
    }


    public  int con(boolean[] noeud ,boolean[][] arc, int ignore , int compteur )
    {
        int comp =0 ;

        for ( int i=0 ; i<compteur ; i++ )  noeud[i]=false; //initialisation

        for ( int i=0 ; i<compteur ; i++ )
            if (i!=ignore)
            {
                if (noeud[i]==false)
                {   comp++;
                    dfs(i,noeud,arc,ignore,compteur);
                }
            }
        return comp ;
    }


    public boolean[] getNoeudArt(int compteur) {
        boolean noeudArt[]= new boolean[100];
        int co = con(noeud,arc ,-1 , compteur) ;
        for ( int i=0 ; i<compteur ; i++ )
        {
            if( con(noeud,arc,i , compteur )>co )
            { noeudArt[i]=true;}
            else noeudArt[i]=false;
        }
            return noeudArt ;

    }

}
