use reqwasm::http::Request;
use serde::{Deserialize, Serialize };
use serde_json::json;

#[derive(Serialize, Deserialize)]
pub struct ApiLoginResponse{
    pub username:String,
    pub token: String,
}

#[derive(Serialize, Deserialize)]
pub struct ApiLoginResponseData{
    pub data: ApiLoginResponse,
}


pub async fn api_login(username:String, password:String) -> ApiLoginResponse{
    let body = json!({
        "username": username,
        "password": password
    });

    let response = Request::post("http://localhost:8000/api/v1/user/login")
        .header("content-type", "application/json")
        .body(body.to_string())
        .send()
        .await
    .unwrap()
    .json::<ApiLoginResponseData>()
    .await
    .unwrap();

    response.data
}