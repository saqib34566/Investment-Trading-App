import java.io.Serializable;
import java.util.ArrayList;

public class Portfolio implements Serializable
{
    private ArrayList<Asset> assets;

    public Portfolio(){
        this.assets = new ArrayList<Asset>();
    }

    public void addAsset(Asset asset){
        this.assets.add(asset);
    }

    public void removeAsset(Asset asset) {
        this.assets.remove(asset);
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    // displays all assets bought by the user
    public String viewPortfolio(){
        String ownedAssets = "";
        for (Asset asset : this.assets) {
            ownedAssets += asset.toStringSell();
        }
        return ownedAssets;
    }

}
