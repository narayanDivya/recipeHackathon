package recipeHackathon.Model;

public enum AllergyType {
	MILK,SOY,ALMOND,EGG,SESAME,PEANUTS,WALNUT,HAZELNUT,PECAN,CASHEW,PISTACHIO,SHELLFISH,SEAFOOD;
	
	// name of key in property file
	public String GetPropertyName() {
		switch(this){
		case ALMOND:
			return "almond";
		case MILK:
			return "milk";
		case SOY:
			return "soy";
		case EGG:
			return "egg";
		case SESAME:
			return "sesame";
		case PEANUTS:
			return "peanuts";
		case WALNUT:
			return "walnuts";
		case HAZELNUT:
			return "hazelnut";
		case PECAN:
			return "pecan";
		case CASHEW:
			return "cashew";
		case PISTACHIO:
			return "pistachio";
		case SHELLFISH:
			return "shellfish";
		case SEAFOOD:
			return "seafood";
		default:
			return "unknown";		
		}
	}
}
