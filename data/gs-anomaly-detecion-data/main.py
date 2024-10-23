from fastapi import FastAPI, status
import uvicorn

app = FastAPI()

@app.get("/test")
def test():
    return status.HTTP_200_OK

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)