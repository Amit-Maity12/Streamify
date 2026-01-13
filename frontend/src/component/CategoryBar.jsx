import "./CategoryBar.css";

const categories = [
  "New",
  "Trending",
  "Top Hits",
  "Pop",
  "Rock",
  "New Year",
  "Recommended"
];

export default function CategoryBar() {
  return (
    <div className="category-bar">
      {categories.map((cat) => (
        <button key={cat} className="category-pill">
          {cat}
        </button>
      ))}
    </div>
  );
}
