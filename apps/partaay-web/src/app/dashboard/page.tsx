"use client";

import Link from "next/link";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { API_BASE_URL } from "@/config/api";

interface User {
  id: number;
  fullName: string;
  email: string;
}

export default function DashboardPage() {
  const router = useRouter();

  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const [loggingOut, setLoggingOut] = useState(false);

  useEffect(() => {
    const loadCurrentUser = async () => {
      try {
        const response = await fetch(`${API_BASE_URL}/auth/me`, {
          method: "GET",
          credentials: "include",
        });

        if (!response.ok) {
          router.replace("/login");
          return;
        }

        const data = await response.json();

        setUser(data);
      } catch (error) {
        console.error("Unable to load current user:", error);

        router.replace("/login");
      } finally {
        setLoading(false);
      }
    };

    loadCurrentUser();
  }, [router]);

  const handleLogout = async () => {
    try {
      setLoggingOut(true);

      await fetch(`${API_BASE_URL}/auth/logout`, {
        method: "POST",
        credentials: "include",
      });
    } catch (error) {
      console.error("Logout error:", error);
    } finally {
      router.replace("/login");
      router.refresh();
    }
  };

  if (loading) {
    return (
      <main className="dashboard-loading">
        <div className="dashboard-spinner" />

        <p>Loading Partaay...</p>
      </main>
    );
  }

  if (!user) {
    return null;
  }

  return (
    <main className="dashboard-page">
      {/* Navigation */}

      <header className="dashboard-header">
        <Link href="/dashboard" className="dashboard-logo">
          Partaay
        </Link>

        <nav className="dashboard-nav">
          <Link href="/dashboard">Home</Link>

          <Link href="/events">Discover</Link>

          <Link href="/events/create">Create Event</Link>
        </nav>

        <div className="dashboard-user">
          <div className="user-avatar">
            {user.fullName?.charAt(0).toUpperCase()}
          </div>

          <div className="user-info">
            <strong>{user.fullName}</strong>

            <span>{user.email}</span>
          </div>

          <button
            type="button"
            className="logout-button"
            onClick={handleLogout}
            disabled={loggingOut}
          >
            {loggingOut ? "Signing out..." : "Logout"}
          </button>
        </div>
      </header>

      {/* Dashboard content */}

      <section className="dashboard-container">
        <section className="dashboard-hero">
          <div>
            <span className="dashboard-label">PARTAAY</span>

            <h1>Welcome, {user.fullName}! 🎉</h1>

            <p>
              Discover what's happening around you, create your own events, and
              bring people together.
            </p>

            <div className="hero-actions">
              <Link href="/events" className="dashboard-primary-button">
                Discover Events
              </Link>

              <Link
                href="/events/create"
                className="dashboard-secondary-button"
              >
                + Create Event
              </Link>
            </div>
          </div>
        </section>

        {/* Quick actions */}

        <section className="dashboard-section">
          <div className="section-heading">
            <div>
              <h2>What would you like to do?</h2>

              <p>Start exploring Partaay.</p>
            </div>
          </div>

          <div className="dashboard-cards">
            <Link href="/events" className="dashboard-card">
              <div className="card-icon">🔎</div>

              <h3>Discover Events</h3>

              <p>
                Find parties, sports events, meetups and experiences near you.
              </p>

              <span>Explore events →</span>
            </Link>

            <Link href="/events/create" className="dashboard-card">
              <div className="card-icon">🎉</div>

              <h3>Create an Event</h3>

              <p>Host your own public or private event and invite people.</p>

              <span>Create event →</span>
            </Link>

            <Link href="/my-events" className="dashboard-card">
              <div className="card-icon">📅</div>

              <h3>My Events</h3>

              <p>Manage events you're hosting and events you've joined.</p>

              <span>View my events →</span>
            </Link>
          </div>
        </section>

        {/* Upcoming events */}

        <section className="dashboard-section">
          <div className="section-heading">
            <div>
              <h2>Your upcoming events</h2>

              <p>Events you're attending or hosting.</p>
            </div>

            <Link href="/my-events">View all</Link>
          </div>

          <div className="empty-events">
            <div className="empty-events-icon">🎊</div>

            <h3>No upcoming events yet</h3>

            <p>Discover an event or create your first Partaay.</p>

            <Link href="/events" className="dashboard-primary-button">
              Find Events
            </Link>
          </div>
        </section>
      </section>
    </main>
  );
}
