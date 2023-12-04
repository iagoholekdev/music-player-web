const mainCard = document.querySelector("#ContentWarpper");
const count = document.querySelector("#CountNumber");
const songImg = document.querySelector("#SongImg");
const controlButtons = document.querySelector(".control");
const currentYear = new Date().getFullYear();

const playPauseButton = document.querySelector("#PausePlay");
const audio = document.querySelector("audio");
const artist = document.querySelector("#Artist");
const songName = document.querySelector("#SongName");
const previousButton = document.querySelector("#Previous");
const nextButton = document.querySelector("#Next");
const songImgAtTheTop = document.querySelector("img");

let startDuration = document.querySelector("#Start");
const endDuration = document.querySelector("#End");
const meter = document.querySelector("#ProgrssMeterChild");
const progressBar = document.querySelector("#ProgressMeterContainer");

let isPlaying = false;
let hasPlayedSongAlready = false;
let index = 0;
let countValue = 0;

const addSongBtn = document.getElementById('addSongBtn');
const songForm = document.getElementById('songForm');

let songDataBase = [
  {
   
  }
];


addSongBtn.addEventListener('click', () => {
  const title = document.getElementById('songTitle').value;
  const artist = document.getElementById('songArtist').value;
  const songSrc = document.getElementById('songSrc').value;
  const imgSrc = document.getElementById('imgSrc').value;

  const newSong = {
    pathToSong: songSrc,
    pathToImage: imgSrc,
    artistName: artist,
    songName: title
};

  console.log(newSong);

  fetch('http://localhost:8080/fileupload/sendnewmusic', {
      method: 'POST',
      body: JSON.stringify(newSong),
      headers: {
        'Content-Type': 'application/json',
    },
  })
  .then(response => {
      // Handle the response from the backend
      console.log('Upload successful:', response);
  })
  .then(() => {
      // Fetch the updated data immediately after sending new music
        fetch('http://localhost:8080/fileupload/upload', {
          method: 'POST',
          body: JSON.stringify(newSong),
          headers: {
              'Content-Type': 'application/json',
          },
           })
          .then(response => response.json())
          .then(updatedData => {
              // Handle the updated data received from the server
              console.log('Updated data from server:', updatedData);

              // Update the songDataBase array with the saved file
              songDataBase = [
                  ...songDataBase,
                  {
                      songSrc: updatedData.pathToSong,
                      title: updatedData.songName,
                      artist: updatedData.artistName,
                      imgSrc: updatedData.pathToImage,
                  }
              ];

              // Now you can use the updated songDataBase array as needed
              console.log('Updated songDataBase:', songDataBase);
          })
          .catch(error => {
              console.error('Error fetching updated data:', error);
          });
  })
  .catch(error => {
      console.error('Error during upload:', error);
  });
});


document.addEventListener("DOMContentLoaded", () => {
  loadObject();
  musicStats();
});

loadObject = () => {

  fetch(`http://localhost:8080/fileupload/getfiles/`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(response => response.json())
      .then(data => {
        // Handle the updated data received from the server
        console.log('Updated data from server:', data);

        // Update the songDataBase array with the saved file
        if (Array.isArray(data)) {
          // Update the songDataBase array with the saved file
          songDataBase = [
              ...songDataBase,
              {
                  songSrc: data[0].pathToSong,
                  title: data[0].songName,
                  artist: data[0].artistName,
                  imgSrc: data[0].pathToImage,
              }
          ];

        // Now you can use the updated songDataBase array as needed
        console.log('loaded songDataBase:', songDataBase);
    }})

}

const musicStats = () => {
  const musicDescElement = document.getElementById('MusicDesc');

  if (musicDescElement) {
    const trackNameElement = musicDescElement.querySelector('#SongName');
    const artistNameElement = musicDescElement.querySelector('#Artist');

    if (trackNameElement && artistNameElement) {
      const trackName = trackNameElement.textContent;
      const artistName = artistNameElement.textContent;

      fetch(`http://localhost:8080/music/getcontador/${encodeURIComponent(trackName)}/${encodeURIComponent(artistName)}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          console.log(data);
          const [countNumber, timesListened] = data;

          // Update the CountNumber element
          document.getElementById('CountNumber').textContent = countNumber.toString();

          // Update the TimesListenedNumber element
          document.getElementById('TimesListenedNumber').textContent = timesListened.toString();
        })
        .catch(error => {
          console.error('Error:', error);
        });
    } else {
      console.error('trackNameElement or artistNameElement not found inside MusicDesc');
    }
  } else {
    console.error('MusicDesc element not found');
  }
}


const loadMusic = () => {
  audio.src = songDataBase[index].songSrc;
  artist.textContent = songDataBase[index].artist;
  songName.textContent = songDataBase[index].title;
  songImgAtTheTop.src = songDataBase[index].imgSrc;
};
audio.addEventListener("ended", () => {
  loadMusic(index++);
  play();
});

loadMusic();

nextButton.addEventListener("click", () => {
  if (index < songDataBase.length - 1) {
    hasPlayedSongAlready = false;
    loadMusic(index++);
    play();
  } else {
    pause();
  }
});

previousButton.addEventListener("click", () => {
  if (index > 0) {
    loadMusic(index--);
    play();
  } else {
    pause();
  }
});



const play = () => {
  isPlaying = true;
  audio.play();
  playPauseButton.classList.replace("fa-play", "fa-pause");
  songImg.classList.add("anime");
  //if (!isPlaying) {
    hasPlayedSongAlready = true;
    const musicDTO = {
      trackName: songName.textContent,
      artistName: artist.textContent,
      dateListened: new Date().toISOString().split('T')[0],
    };

    fetch('http://localhost:8080/music/sendmusic', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    body: JSON.stringify(musicDTO),
    })
    .then(response => {
      musicStats();
    });
//  }
};

const pause = () => {
  isPlaying = false;
  audio.pause();
  playPauseButton.classList.replace("fa-pause", "fa-play");
  songImg.classList.remove("anime");
};

playPauseButton.addEventListener("click", () => {
  if (isPlaying) {
    pause();
  } else {
    play();
  }
});
let minute, second;
const timeStamp = (event) => {
  let { duration, currentTime } = event.srcElement;
  const full_second = Math.floor(duration % 60);
  const full_minute = Math.floor(duration / 60);
  const start_second = Math.floor(currentTime % 60);
  const start_minute = Math.floor(currentTime / 60);
  const totalDuration = `${full_minute} : ${full_second}`;
  const currenDuration = `${start_minute} : ${start_second}`;
  if (duration) {
    endDuration.textContent = totalDuration;
  }
  startDuration.textContent = currenDuration;
  const percentage = (currentTime / duration) * 100;
  meter.style.width = `${percentage}%`;
};
audio.addEventListener("timeupdate", timeStamp);
progressBar.addEventListener("click", (event) => {
  const { duration } = audio;
  const moreProgress =
    (event.offsetX / event.srcElement.clientWidth) * duration;
  audio.currentTime = moreProgress;
});

document.querySelector("#Year").innerHTML = currentYear;

//mainCard.addEventListener("mouseover", (event) => {
 // const xAxis = (window.innerWidth / 2 - event.pageX) / 15;
  //const yAxis = (window.innerHeight / 2 - event.pageY) / 15;
 // mainCard.style.transform = `rotateX(${yAxis}deg) rotateY(${xAxis}deg)`;
 // songImg.style.transform = `rotate(${xAxis}deg)`;
 // controlButtons.style.transform = `rotate(${xAxis}deg)`;//
//});
//mainCard.addEventListener("mouseleave", () => {
//  mainCard.style.transform = "rotateX(0deg) rotateY(0deg)";
 // songImg.style.transform = "rotate(0deg)";
 // controlButtons.style.transform = "rotate(0deg)";//
//});
