// import { useState } from "react";
// import "./SongCarousel.css";

// const songs = [
//   { id: 1, title: "Dreams", img: "https://i.scdn.co/image/ab67616d0000b273e52a59a28efa4773dd2bfe1b" },
//   { id: 2, title: "Night Vibe", img: "/assets/song2.jpg" },
//   { id: 3, title: "Midnight", img: "/assets/song3.jpg" },
//   { id: 4, title: "Energy", img: "/assets/song4.jpg" },
//   { id: 5, title: "Relax", img: "/assets/song5.jpg" },
// ];

// export default function SongCarousel() {
//   const [active, setActive] = useState(2); // center index

//   const getPosition = (index) => {
//     const diff = (index - active + songs.length) % songs.length;

//     if (diff === 0) return "center";
//     if (diff === 1) return "right1";
//     if (diff === songs.length - 1) return "left1";
//     if (diff === 2) return "right2";
//     return "left2";
//   };

//   const next = () => setActive((prev) => (prev + 1) % songs.length);
//   const prev = () =>
//     setActive((prev) => (prev - 1 + songs.length) % songs.length);

//   return (
//     <div className="carousel-wrapper">
//       <button className="nav-btn" onClick={prev}>◀</button>

//       <div className="carousel">
//         {songs.map((song, index) => (
//           <div key={song.id} className={`card ${getPosition(index)}`}>
//             <img src={song.img} alt={song.title} />
//             <h4>{song.title}</h4>
//           </div>
//         ))}
//       </div>

//       <button className="nav-btn" onClick={next}>▶</button>
//     </div>
//   );
// }

import { useEffect, useState } from "react";
import "./SongCarousel.css";

export default function SongCarousel() {
  console.log("SongCarousel rendered");
  const [songs, setSongs] = useState([]);
  const [active, setActive] = useState(2);

  useEffect(() => {
    fetch("http://localhost:8080/api/songs/song-carousel", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify([
          "Dooron dooron",
          "Kesariya",
          "Raabta",
          "Agar Tum Saath Ho",
          "Phir Bhi Tumko Chaahunga"
        ])
      })
      .then((res) => res.json())
      .then(setSongs)
      .catch(console.error);
  }, []);

  if (songs.length === 0) return null;

  const getPosition = (index) => {
    if (songs.length === 1) return "center";

    const diff = (index - active + songs.length) % songs.length;
    if (diff === 0) return "center";
    if (diff === 1) return "right1";
    if (diff === songs.length - 1) return "left1";
    if (diff === 2) return "right2";
    return "left2";
  };

  const next = () =>
    setActive((prev) => (prev + 1) % songs.length);

  const prev = () =>
    setActive((prev) => (prev - 1 + songs.length) % songs.length);

  return (
    <div className="carousel-wrapper">
      <button className="nav-btn" onClick={prev}>◀</button>

      <div className="carousel">
        {songs.map((song, index) => (
          <div key={song.id} className={`card ${getPosition(index)}`}>
            <img src={song.imgUrl} alt={song.title} />
            <h4>{song.title}</h4>
          </div>
        ))}
      </div>

      <button className="nav-btn" onClick={next}>▶</button>
    </div>
  );
}
