import AppRouter from "./router/AppRouter";
import NavBar from "./components/NavBar";

function App() {
  return (
    <div className="min-h-screen flex flex-col">
      <NavBar />
      <main className="flex-grow container mx-auto p-4">
        <AppRouter />
      </main>
    </div>
  );
}

export default App;
