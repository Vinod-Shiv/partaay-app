INSERT INTO events
(
    id,
    host_id,
    title,
    description,
    starts_at,
    ends_at,
    venue_name,
    city,
    visibility,
    capacity,
    status,
    image_url,
    created_at,
    updated_at
)
VALUES

    (
        gen_random_uuid(),
        'd57f6ab9-5b12-4b27-9aed-1c680e02f08d',
        'Charlotte Rooftop Party',
        'Enjoy a rooftop evening with music, food, friends, and beautiful Charlotte skyline views.',
        CURRENT_TIMESTAMP + INTERVAL '3 days',
        CURRENT_TIMESTAMP + INTERVAL '3 days 4 hours',
        'Skyline Rooftop',
        'Charlotte, NC',
        'PUBLIC',
        150,
        'SCHEDULED',
        'https://images.unsplash.com/photo-1492684223066-81342ee5ff30',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),

    (
        gen_random_uuid(),
        'd57f6ab9-5b12-4b27-9aed-1c680e02f08d',
        'Panthers Game Watch Party',
        'Watch the game with football fans on big screens with food and a fun game-day atmosphere.',
        CURRENT_TIMESTAMP + INTERVAL '5 days',
        CURRENT_TIMESTAMP + INTERVAL '5 days 4 hours',
        'Uptown Sports Bar',
        'Charlotte, NC',
        'PUBLIC',
        100,
        'SCHEDULED',
        'https://images.unsplash.com/photo-1508098682722-e99c43a406b2',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),

    (
        gen_random_uuid(),
        'd57f6ab9-5b12-4b27-9aed-1c680e02f08d',
        'Friday Night Live Music',
        'Enjoy live music, food, drinks, and a great social atmosphere in NoDa.',
        CURRENT_TIMESTAMP + INTERVAL '7 days',
        CURRENT_TIMESTAMP + INTERVAL '7 days 4 hours',
        'NoDa Music Hall',
        'Charlotte, NC',
        'PUBLIC',
        250,
        'SCHEDULED',
        'https://images.unsplash.com/photo-1501386761578-eac5c94b800a',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),

    (
        gen_random_uuid(),
        'd57f6ab9-5b12-4b27-9aed-1c680e02f08d',
        'Weekend Food Festival',
        'Discover local restaurants, food trucks, desserts, entertainment, and music.',
        CURRENT_TIMESTAMP + INTERVAL '10 days',
        CURRENT_TIMESTAMP + INTERVAL '10 days 6 hours',
        'South End Festival Grounds',
        'Charlotte, NC',
        'PUBLIC',
        500,
        'SCHEDULED',
        'https://images.unsplash.com/photo-1555939594-58d7cb561ad1',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),

    (
        gen_random_uuid(),
        'd57f6ab9-5b12-4b27-9aed-1c680e02f08d',
        'Saturday Morning Hiking Group',
        'Meet new people while enjoying a weekend hike. Beginners are welcome.',
        CURRENT_TIMESTAMP + INTERVAL '12 days',
        CURRENT_TIMESTAMP + INTERVAL '12 days 4 hours',
        'Crowders Mountain State Park',
        'Kings Mountain, NC',
        'PUBLIC',
        40,
        'SCHEDULED',
        'https://images.unsplash.com/photo-1551632811-561732d1e306',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),

    (
        gen_random_uuid(),
        'd57f6ab9-5b12-4b27-9aed-1c680e02f08d',
        'Tech & AI Networking Night',
        'Connect with developers, entrepreneurs, technology professionals, and AI enthusiasts.',
        CURRENT_TIMESTAMP + INTERVAL '14 days',
        CURRENT_TIMESTAMP + INTERVAL '14 days 3 hours',
        'Innovation Center',
        'Charlotte, NC',
        'PUBLIC',
        120,
        'SCHEDULED',
        'https://images.unsplash.com/photo-1515187029135-18ee286d815b',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    );