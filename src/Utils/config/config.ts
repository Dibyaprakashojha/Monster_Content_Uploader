import { environment } from "src/environments/environment";

export class GlobalConfig {

    private static _instance: GlobalConfig = new GlobalConfig();
    public static config:any;

    constructor() {
        if(GlobalConfig._instance) {
            throw new Error("Error: Instantiation of config failed.");
        }
        GlobalConfig._instance = this;
    }

    public static getInstance(): GlobalConfig {
        return GlobalConfig._instance;
    }


    public static setConfig(environmentConfig:any) {
        GlobalConfig.config = environmentConfig;
    }

   
    
}
